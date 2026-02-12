import { useNavigate, useParams } from "react-router-dom";
import { skipToken } from "@reduxjs/toolkit/query";
import { useGetUserByIdQuery } from "../redux/user/userApi";
import { ROUTES } from "../routes/routePaths";

export function UserInfo() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: user, error, isLoading } = useGetUserByIdQuery(id ?? skipToken);

  if (!id) return <div>Invalid user id</div>;
  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error</div>;
  if (!user) return <div>No user found</div>;

  return (
    <>
      <button type="button" onClick={() => navigate(ROUTES.HOME)}>
        Back
      </button>

      <div>
        {user.firstName} {user.lastName} ({user.id})
      </div>

      <div>
        <div>Contact Information</div>
        <div>Email: {user.email}</div>
        <div>Phone: {user.phone}</div>
      </div>

      <div>
        <div>Personal Information</div>
        <div>Age: {user.age}</div>
      </div>
    </>
  );
}
