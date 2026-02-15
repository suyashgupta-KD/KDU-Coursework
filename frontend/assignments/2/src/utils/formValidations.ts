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
  if (values.selectedTypeId === "") return "Please select type of cleaning.";
  if (values.selectedFrequencyId === "")
    return "Please select cleaning frequency.";
  if (values.timeline.hours < 1) return "Please select number of hours.";
  if (values.timeline.date.trim() === "") return "Please select cleaning date.";
  if (values.selectedSlotId === "") return "Please select a start time.";
  if (values.paymentDetails.cardNumber.trim() === "")
    return "Please enter card number.";
  if (values.paymentDetails.expiry.trim() === "")
    return "Please enter card expiry.";
  if (values.paymentDetails.cvv.trim() === "") return "Please enter CVV.";
  if (values.paymentDetails.nameOnCard.trim() === "")
    return "Please enter name on card.";
  if (values.personal.email.trim() === "") return "Please enter email.";
  if (values.personal.phone.trim() === "") return "Please enter phone number.";
  if (values.personal.address.trim() === "") return "Please enter address.";
  if (values.personal.pincode <= 0) return "Please enter valid pincode.";
  if (!values.agreedToTerms)
    return "Please agree to terms and conditions.";

  return "";
}
