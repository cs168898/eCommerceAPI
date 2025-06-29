export type Cart = {
  id: number;
  totalPrice: number | null;
  items: any[]; // You can define a type for items if needed
  currency: string;
};

export type User = {
  id: number;
  username: string;
  email: string;
  phoneNumber: string | null;
  shippingAddress: string | null;
  billingAddress: string | null;
  cart: Cart;
  role: string;
  enabled: boolean;
};