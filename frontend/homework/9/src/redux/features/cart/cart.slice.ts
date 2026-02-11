import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { CartItem } from "../../../types/cartItem";
import type { Product } from "../../../types/product";
import { getDiscountedPrice } from "../../../utils/pricing";

type CartState = {
  items: CartItem[];
  totalItems: number;
  totalPrice: number;
};

const initialState: CartState = {
  items: [],
  totalItems: 0,
  totalPrice: 0,
};

function recalcTotals(state: CartState) {
  state.totalItems = state.items.reduce((sum, item) => sum + item.quantity, 0);
  state.totalPrice = state.items.reduce(
    (sum, item) =>
      sum +
      getDiscountedPrice(
        item.product.price,
        item.product.discountPercentage,
      ) *
        item.quantity,
    0,
  );
}

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart(state, action: PayloadAction<Product>) {
      const product = action.payload;
      if (product.stock <= 0) {
        return;
      }

      const existing = state.items.find((i) => i.product.id === product.id);
      if (existing) {
        if (existing.quantity < product.stock) {
          existing.quantity += 1;
        }
      } else {
        state.items.push({ product, quantity: 1 });
      }
      recalcTotals(state);
    },
    removeFromCart(state, action: PayloadAction<number>) {
      const id = action.payload;
      state.items = state.items.filter((i) => i.product.id !== id);
      recalcTotals(state);
    },
    updateQuantity(
      state,
      action: PayloadAction<{ id: number; quantity: number }>,
    ) {
      const { id, quantity } = action.payload;
      const item = state.items.find((i) => i.product.id === id);
      if (!item) return;

      if (quantity <= 0) {
        state.items = state.items.filter((i) => i.product.id !== id);
      } else {
        const maxStock = item.product.stock;
        if (maxStock <= 0) {
          state.items = state.items.filter((i) => i.product.id !== id);
        } else {
          item.quantity = Math.min(quantity, maxStock);
        }
      }
      recalcTotals(state);
    },
    clearCart(state) {
      state.items = [];
      state.totalItems = 0;
      state.totalPrice = 0;
    },
  },
});

export const { addToCart, removeFromCart, updateQuantity, clearCart } =
  cartSlice.actions;
export default cartSlice.reducer;
