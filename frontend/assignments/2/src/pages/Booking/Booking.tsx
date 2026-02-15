import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import type { AppDispatch } from "../../app/store";
import SelectionTab from "../../components/common/selectionTabs/SelectionTab";
import { Navbar } from "../../components/navbar/Navbar";
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
    }
  };

  if (isLoading) {
    return <DotLoader />;
  }

  if (isError || !config) {
    return <p>Failed to load booking configuration.</p>;
  }

  return (
    <main>
      <Navbar />
      <div className={styles.bookingPage}>
        <section className={styles.bookingDetails}>
          {formMessage && <p className={styles.formMessage}>{formMessage}</p>}

          <section>
            <h2>What type of cleaning?</h2>
            <div>
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

          <section>
            <h2>How often would you like cleaning?</h2>
            <div>
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

          <section>
            <h2>Tell us about your home</h2>
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
          </section>

          <section>
            <h2>How many hours?</h2>
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
          </section>

          <section>
            <h2>When do you like to start?</h2>
            <div>
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

          <section>
            <h2>Need any extras?</h2>
            <div>
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
              placeholder="Special requirements (optional)"
              value={notes}
              onChange={(event) => setNotes(event.target.value)}
            />
          </section>

          <PaymentForm value={paymentDetails} onChange={onPaymentChange} />

          <PersonalDetailsForm value={personal} onChange={onPersonalChange} />

          <label>
            <input
              type="checkbox"
              checked={agreedToTerms}
              onChange={(event) => setAgreedToTerms(event.target.checked)}
            />{" "}
            I read and agree to the terms & conditions
          </label>

          <button type="button" onClick={onSubmit}>
            {isSubmitting ? "Submitting..." : "Complete Booking"}
          </button>
        </section>
        <section>
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
