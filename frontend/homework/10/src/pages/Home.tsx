import { UserCard } from "../components/user/UserCard";
import { AddUser } from "../components/user/AddUser";
import { useGetUsersQuery } from "../redux/user/userApi";
import styles from "./Home.module.css";

export function Home() {
  const { data, error, isLoading } = useGetUsersQuery();

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error</div>;

  return (
    <div className={styles.page}>
      <AddUser />
      <div className={styles.list}>
        {data?.users.map((u) => (
          <UserCard key={u.id} user={u} />
        ))}
      </div>
    </div>
  );
}
