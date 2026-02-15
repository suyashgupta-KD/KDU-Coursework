import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import type { AppDispatch } from "../../app/store";
import SelectionTab from "../../components/common/selectionTabs/SelectionTab";
import { PaymentForm } from "../../components/payment/PaymentForm";
import type { PaymentDetails } from "../../components/payment/PaymentForm";
import { PersonalDetailsForm } from "../../components/personalDetails/PersonalDetailsForm";
import { RoomCounter } from "../../components/rooms/RoomCounter";
import { SummaryTab } from "../../components/summary/SummaryTab";
import DateSelector from "../../components/timeline/date/DateSelector";
import { HourCounter } from "../../components/timeline/hours/HourCounter";
import { useGetBookingConfigQuery } from "../../features/bookingConfig/bookingConfigApi";
import { selectBookingTimeline } from "../../features/bookingTimeline/bookingTimelineSelectors";
import { setBookingTimeline } from "../../features/bookingTimeline/bookingTimelineSlice";
import { useCreateBookingMutation } from "../../features/createBooking/bookingApi";
import { selectPersonalDetails } from "../../features/personalDetails/personalDetailsSelectors";
import { setPersonalDetails } from "../../features/personalDetails/personalDetailsSlice";
import { ROUTES } from "../../routes/routePaths";
import type { Booking } from "../../types/Booking";
import type { PersonalDetails } from "../../types/PersonalDetails";
import { calculatePrice } from "../../utils/calculatePrice";
import { getFirstMissingFieldMessage } from "../../utils/formValidations";
import styles from "./Booking.module.scss";
import { DotLoader } from "react-spinners";
const defaultPaymentDetails: PaymentDetails = {
  cardNumber: "",
  expiry: "",
  cvv: "",
  nameOnCard: "",
};

export function Booking() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const timeline = useSelector(selectBookingTimeline);
  const personal = useSelector(selectPersonalDetails);

  const { data: config, isLoading, isError } = useGetBookingConfigQuery();
  const [createBooking, { isLoading: isSubmitting }] =
    useCreateBookingMutation();

  const [selectedTypeId, setSelectedTypeId] = useState("");
  const [selectedFrequencyId, setSelectedFrequencyId] = useState("");
  const [selectedSlotId, setSelectedSlotId] = useState("");
  const [selectedExtras, setSelectedExtras] = useState<string[]>([]);
  const [bedrooms, setBedrooms] = useState(1);
  const [bathrooms, setBathrooms] = useState(1);
  const [notes, setNotes] = useState("");
  const [agreedToTerms, setAgreedToTerms] = useState(false);
  const [paymentDetails, setPaymentDetails] = useState<PaymentDetails>(
    defaultPaymentDetails,
  );
  const [formMessage, setFormMessage] = useState("");

  const totalPrice = calculatePrice({
    config: config ?? null,
    selectedTypeId,
    selectedFrequencyId,
    selectedExtras,
    bedrooms,
    bathrooms,
  });

  const selectedTypeLabel =
    config?.cleaningTypes.find((item) => item.id === selectedTypeId)?.label ||
    "";
  const selectedFrequencyLabel =
    config?.frequencies.find((item) => item.id === selectedFrequencyId)
      ?.label || "";

  const onPersonalChange = (field: keyof PersonalDetails, value: string) => {
    if (field === "pincode") {
      dispatch(
        setPersonalDetails({
          ...personal,
          pincode: Number(value),
        }),
      );
      return;
    }

    dispatch(
      setPersonalDetails({
        ...personal,
        [field]: value,
      }),
    );
  };

  const onPaymentChange = (field: keyof PaymentDetails, value: string) => {
    setPaymentDetails((previous) => ({
      ...previous,
      [field]: value,
    }));
  };

  const onExtraSelect = (extraId: string) => {
    setSelectedExtras((previous) => {
      if (previous.includes(extraId)) {
        return previous.filter((item) => item !== extraId);
      }
      return [...previous, extraId];
    });
  };

  const onSubmit = async () => {
    setFormMessage("");

    if (!config || isSubmitting) {
      return;
    }

    const missingFieldMessage = getFirstMissingFieldMessage({
      selectedTypeId,
      selectedFrequencyId,
      selectedSlotId,
      timeline,
      paymentDetails,
      personal,
      agreedToTerms,
    });

    if (missingFieldMessage) {
      setFormMessage(missingFieldMessage);
      window.scrollTo({ top: 0, behavior: "smooth" });
      return;
    }

    const payload: Booking = {
      typeId: selectedTypeId as Booking["typeId"],
      frequencyId: selectedFrequencyId as Booking["frequencyId"],
      bedrooms,
      bathrooms,
      hours: timeline.hours,
      date: timeline.date,
      slotId: selectedSlotId,
      email: personal.email,
      phone: personal.phone,
      address: personal.address,
      pincode: personal.pincode,
      extras: selectedExtras as Booking["extras"],
      notes,
      totalPrice,
    };

    try {
      const response = await createBooking(payload).unwrap();
      navigate(ROUTES.CONFIRMATION, { state: response });
    } catch {
      setFormMessage("Booking failed. Please try again.");
      window.scrollTo({ top: 0, behavior: "smooth" });
    }
  };

  if (isLoading) {
    return (
      <div className={styles.loaderWrapper}>
        <DotLoader color="var(--loader-color)" />
      </div>
    );
  }

  if (isError || !config) {
    return <p>Failed to load booking configuration.</p>;
  }

  return (
    <main>
      <div className={styles.bookingPage}>
        <section className={styles.bookingDetails}>
          {formMessage && <p className={styles.formMessage}>{formMessage}</p>}

          <section className={styles.formSection}>
            <h1>What type of cleaning?</h1>
            <div className={`${styles.optionRow} ${styles.tabRow}`}>
              {config.cleaningTypes.map((item) => (
                <SelectionTab
                  key={item.id}
                  label={item.label}
                  value={item.id}
                  isSelected={selectedTypeId === item.id}
                  onSelect={setSelectedTypeId}
                />
              ))}
            </div>
          </section>

          <section className={styles.formSection}>
            <h1>How often would you like cleaning?</h1>
            <div className={`${styles.optionRow} ${styles.tabRow}`}>
              {config.frequencies.map((item) => (
                <SelectionTab
                  key={item.id}
                  label={item.label}
                  value={item.id}
                  isSelected={selectedFrequencyId === item.id}
                  onSelect={setSelectedFrequencyId}
                />
              ))}
            </div>
          </section>

          <section className={styles.formSection}>
            <h1>Tell us about your home</h1>
            <div className={styles.optionRow}>
              <RoomCounter
                id="bedroom"
                label="Bedrooms"
                count={bedrooms}
                onIncrease={() => setBedrooms((previous) => previous + 1)}
                onDecrease={() =>
                  setBedrooms((previous) => Math.max(0, previous - 1))
                }
              />
              <RoomCounter
                id="bathroom"
                label="Bathrooms"
                count={bathrooms}
                onIncrease={() => setBathrooms((previous) => previous + 1)}
                onDecrease={() =>
                  setBathrooms((previous) => Math.max(0, previous - 1))
                }
              />
            </div>
          </section>

          <section className={styles.formSection}>
            <h1>Choose hours and date</h1>
            <div className={styles.optionRow}>
              <HourCounter
                hours={timeline.hours}
                increase={() =>
                  dispatch(
                    setBookingTimeline({
                      ...timeline,
                      hours: timeline.hours + 1,
                    }),
                  )
                }
                decrease={() =>
                  dispatch(
                    setBookingTimeline({
                      ...timeline,
                      hours: Math.max(0, timeline.hours - 1),
                    }),
                  )
                }
              />
              <DateSelector />
            </div>
          </section>

          <section className={styles.formSection}>
            <h1>When do you like to start?</h1>
            <div className={`${styles.optionRow} ${styles.tabRow}`}>
              {config.timeSlots.map((item) => (
                <SelectionTab
                  key={item.id}
                  label={item.label}
                  value={item.id}
                  isSelected={selectedSlotId === item.id}
                  onSelect={setSelectedSlotId}
                  disabled={!item.available}
                />
              ))}
            </div>
          </section>

          <section className={styles.formSection}>
            <h1>Need any extras?</h1>
            <div className={`${styles.optionRow} ${styles.tabRow}`}>
              {config.extras.map((item) => (
                <SelectionTab
                  key={item.id}
                  label={item.label}
                  value={item.id}
                  isSelected={selectedExtras.includes(item.id)}
                  onSelect={onExtraSelect}
                />
              ))}
            </div>
            <textarea
              className={styles.notesInput}
              placeholder="Special requirements (optional)"
              value={notes}
              onChange={(event) => setNotes(event.target.value)}
            />
          </section>

          <PaymentForm value={paymentDetails} onChange={onPaymentChange} />

          <PersonalDetailsForm value={personal} onChange={onPersonalChange} />

          <label className={styles.termsRow}>
            <input
              type="checkbox"
              checked={agreedToTerms}
              onChange={(event) => setAgreedToTerms(event.target.checked)}
            />{" "}
            I read and agree to the terms & conditions
          </label>

          <button
            type="button"
            onClick={onSubmit}
            className={styles.submitButton}
          >
            {isSubmitting ? "Submitting..." : "Complete Booking"}
          </button>
        </section>
        <section className={styles.summarySection}>
          <SummaryTab
            cleaningType={selectedTypeLabel}
            frequency={selectedFrequencyLabel}
            totalPrice={totalPrice}
          />
        </section>
      </div>
    </main>
  );
}
