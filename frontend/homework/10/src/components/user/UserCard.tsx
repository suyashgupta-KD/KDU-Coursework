import { useNavigate } from "react-router-dom";
import type { User } from "../../types/User";
import styles from "./UserCard.module.css";

interface UserProps {
  user: User;
}
export function UserCard({ user }: UserProps) {
  const navigate = useNavigate();
  function handleClick() {
    navigate(`/users/${user.id}`);
  }
  return (
    <div role="button" onClick={handleClick} className={styles.card}>
      <img className={styles.avatar} src={user.image} alt={user.firstName} />
      <div className={styles.info}>
        <div className={styles.name}>
          {user.firstName} {user.lastName}
        </div>
        <div className={styles.meta}>Age: {user.age}</div>
        <div className={styles.meta}>{user.email}</div>
        <div className={styles.meta}>{user.phone}</div>
      </div>
    </div>
  );
}
