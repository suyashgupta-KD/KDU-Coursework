import { type ChangeEvent, type FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/store";
import {
  selectEventError,
  selectLoading,
} from "../../features/event/event.selector";
import { addNewEvent } from "../../features/event/event.thunks";
import { buildStatusPagePath } from "../../routes/routePaths";
import styles from "./EventRegistration.module.scss";

type RegistrationForm = {
  name: string;
  email: string;
  event: string;
  message: string;
};

const initialFormValues: RegistrationForm = {
  name: "",
  email: "",
  event: "",
  message: "",
};

export function EventRegistration() {
  const [formValues, setFormValues] =
    useState<RegistrationForm>(initialFormValues);
  const [formError, setFormError] = useState<string | null>(null);

  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const isLoading = useAppSelector(selectLoading);
  const apiError = useAppSelector(selectEventError);

  const handleChange = (
    formEvent: ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >,
  ) => {
    const { name, value } = formEvent.target;
    setFormValues((current) => ({
      ...current,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setFormError(null);

    const name = formValues.name.trim();
    const email = formValues.email.trim();
    const selectedEvent = formValues.event.trim();

    if (!name || !email || !selectedEvent) {
      setFormError("Name, email and event are required.");
      return;
    }

    if (!email.includes("@")) {
      setFormError("Please enter a valid email address.");
      return;
    }

    const regId = "" + Math.floor(Math.random() * 1001);

    try {
      const response = await dispatch(
        addNewEvent({
          regId,
          name,
          email,
          event: selectedEvent,
          message: formValues.message.trim() || undefined,
        }),
      ).unwrap();

      navigate(buildStatusPagePath(response.regId));
    } catch (error) {
      if (typeof error === "string") {
        setFormError(error);
      } else {
        setFormError("Failed to register.");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className={styles.form}>
      <h2>Event Registration</h2>

      <label>Name </label>
      <input
        id="name"
        name="name"
        type="text"
        value={formValues.name}
        onChange={handleChange}
      />

      <label>Email </label>
      <input
        id="email"
        name="email"
        type="email"
        value={formValues.email}
        onChange={handleChange}
      />

      <label>Select Event </label>
      <select
        id="event"
        name="event"
        value={formValues.event}
        onChange={handleChange}
      >
        <option value="">Choose an event</option>
        <option value="conference">Conference</option>
        <option value="hackathon">Hackathon</option>
        <option value="fest">Fest</option>
        <option value="kdu">KDU</option>
      </select>

      <label>Message (optional)</label>
      <textarea
        id="message"
        name="message"
        value={formValues.message}
        onChange={handleChange}
        rows={4}
      />

      {formError && <p>{formError}</p>}
      {apiError && <p>{apiError}</p>}

      <button type="submit" disabled={isLoading}>
        {isLoading ? "Registering..." : "Register for Event"}
      </button>
    </form>
  );
}
