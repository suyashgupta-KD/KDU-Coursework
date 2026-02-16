import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/store";
import { RegistrationStatus } from "../../components/registrationStatus/RegistrationStatus";
import {
  selectEventError,
  selectLoading,
  selectStatusResponse,
} from "../../features/event/event.selector";
import { fetchRegistrationStatus } from "../../features/event/event.thunks";
import styles from "./Status.module.scss";

export function Status() {
  const dispatch = useAppDispatch();
  const { id } = useParams();

  const statusData = useAppSelector(selectStatusResponse);
  const isLoading = useAppSelector(selectLoading);
  const error = useAppSelector(selectEventError);

  if (!id) {
    return <p>Registration ID is missing in the URL.</p>;
  }

  useEffect(() => {
    if (!id) {
      return;
    }

    void dispatch(fetchRegistrationStatus(id));
  }, [dispatch, id]);

  return (
    <div className={styles.form}>
      <RegistrationStatus
        data={statusData}
        isLoading={isLoading}
        error={error}
      />
    </div>
  );
}
