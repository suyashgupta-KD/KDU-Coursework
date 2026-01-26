# Smart Home Backend (API v1)

Minimal Spring Boot 3 / JWT backend for managing houses, rooms, and devices with soft deletes and optimistic locking.

## Running
```
mvn clean spring-boot:run -DskipTests
```
Base URL: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui/index.html`  
OpenAPI JSON: `/v3/api-docs`

Auth header: `Authorization: Bearer <jwt>`

## Auth
| Method | Path | Body | Notes |
| --- | --- | --- | --- |
| POST | /api/v1/auth/register | `{email,password,name}` | Creates user |
| POST | /api/v1/auth/login | `{email,password}` | Returns `token` |

## Houses
| Method | Path | Body | Roles | Notes |
| --- | --- | --- | --- | --- |
| POST | /api/v1/houses | `{name,address}` | Any | Creator becomes ADMIN |
| GET | /api/v1/houses?page=&size= | — | Member/Admin | Lists current user houses (paged, default size 20) |
| PATCH | /api/v1/houses/{plotId}/address | `{address}` | Admin | Address optional in payload; ignored if blank |
| DELETE | /api/v1/houses/{plotId} | — | Admin | Soft deletes house, rooms, devices, memberships |

## Members & Ownership
| Method | Path | Body | Roles | Notes |
| --- | --- | --- | --- | --- |
| POST | /api/v1/houses/{plotId}/members | `{userId}` | Admin | Adds MEMBER; duplicate => 409 |
| POST | /api/v1/houses/{plotId}/transfer-ownership | `{newAdminUserId}` | Admin | Swaps roles: new ADMIN, old becomes MEMBER |

## Rooms
| Method | Path | Body | Roles | Notes |
| --- | --- | --- | --- | --- |
| POST | /api/v1/houses/{plotId}/rooms | `{name}` | Admin | Name unique per house |
| GET | /api/v1/houses/{plotId}/rooms?page=&size= | — | Member/Admin | Paged (default 20) |
| DELETE | /api/v1/houses/{plotId}/rooms/{roomId} | — | Admin | Soft deletes room; devices are unassigned (not deleted) |

## Devices
| Method | Path | Body | Roles | Notes |
| --- | --- | --- | --- | --- |
| POST | /api/v1/houses/{plotId}/devices | `{kickstonId,deviceUsername,devicePassword}` | Admin | Validates against inventory; blocks active duplicate |
| PATCH | /api/v1/houses/{plotId}/devices/{deviceId}/assign-room | `{roomId}` | Admin | Same house required |
| PATCH | /api/v1/houses/{plotId}/devices/{deviceId}/move | `{targetRoomId}` | Member/Admin | Same house only |
| GET | /api/v1/houses/{plotId}/rooms-with-devices | — | Member/Admin | Rooms + devices + unassigned list |
| DELETE | /api/v1/houses/{plotId}/devices/{deviceId} | — | Admin | Soft delete; re-registration allowed after delete |

## Behavior & Rules
- Soft delete everywhere (`deleted_date`); queries filter on `deleted_date IS NULL`.
- Optimistic locking on houses, rooms, devices, house_members. Conflicts return 409 with message “Update conflict, please retry”.
- Device uniqueness enforced by partial index on active devices; soft-deleted devices can be re-registered.
- Roles are per-house: ADMIN or MEMBER stored in `house_members`.
- JWT carries `user_id` only; roles always checked from DB.

## Pagination defaults
- Houses: size 20
- Rooms: size 20
- Rooms-with-devices: unpaged (house scope)

## Error responses (JSON)
- 400 validation/malformed JSON
- 401 invalid/missing token
- 403 forbidden (role/membership)
- 404 not found
- 409 conflict (duplicate, optimistic lock, data constraint)

## Test Data & Inventory
- Device inventory seeded via `src/main/resources/db/dml/01_device_inventory_seed.sql` (idempotent). Use one of the seeded `kickston_id/username/password` combinations when registering devices.

## Concurrency
- Client should retry on HTTP 409 for write operations touching versioned entities (house, room, device, house_member).

