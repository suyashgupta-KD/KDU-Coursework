import type { PaymentDetails } from "../components/payment/PaymentForm";
import type { PersonalDetails } from "../types/PersonalDetails";
import type { BookingTimeline } from "../types/BookingTimeline";

type BookingFormValues = {
  selectedTypeId: string;
  selectedFrequencyId: string;
  selectedSlotId: string;
  timeline: BookingTimeline;
  paymentDetails: PaymentDetails;
  personal: PersonalDetails;
  agreedToTerms: boolean;
};

export function getFirstMissingFieldMessage(values: BookingFormValues) {
  const cardNumber = values.paymentDetails.cardNumber.replace(/\s/g, "");
  const expiry = values.paymentDetails.expiry.trim();
  const cvv = values.paymentDetails.cvv.trim();
  const nameOnCard = values.paymentDetails.nameOnCard.trim();
  const email = values.personal.email.trim();
  const phone = values.personal.phone.trim();
  const address = values.personal.address.trim();

  if (values.selectedTypeId === "") return "Please select type of cleaning.";
  if (values.selectedFrequencyId === "")
    return "Please select cleaning frequency.";
  if (values.timeline.hours < 1) return "Please select number of hours.";
  if (values.timeline.date.trim() === "") return "Please select cleaning date.";
  if (values.selectedSlotId === "") return "Please select a start time.";
  if (cardNumber === "") return "Please enter card number.";
  if (!/^\d{16}$/.test(cardNumber))
    return "Card number must be 16 digits.";

  if (expiry === "") return "Please enter card expiry.";
  if (!/^\d{2}\/\d{2}$/.test(expiry)) return "Expiry must be in MM/YY format.";
  if (Number(expiry.slice(0, 2)) < 1 || Number(expiry.slice(0, 2)) > 12)
    return "Expiry month must be between 01 and 12.";

  if (cvv === "") return "Please enter CVV.";
  if (!/^\d{3,4}$/.test(cvv)) return "CVV must be 3 or 4 digits.";

  if (nameOnCard === "") return "Please enter name on card.";

  if (email === "") return "Please enter email.";
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email))
    return "Please enter a valid email address.";

  if (phone === "") return "Please enter phone number.";
  if (!/^\d{10}$/.test(phone)) return "Phone number must be 10 digits.";

  if (address === "") return "Please enter address.";

  if (values.personal.pincode < 10000 || values.personal.pincode > 99999)
    return "Pincode must be 5 digits.";

  if (!values.agreedToTerms) return "Please agree to terms and conditions.";

  return "";
}
