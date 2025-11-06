export interface Icandy {
  id: number;
  name: string;
  price: number;
  description: string;
  type: CandyType;
  imageKey?: String;
  imageFile?: File;
}

export const CandyType = {
  CAKE: "CAKE",
  MUFFIN: "MUFFIN",
  BROWNIE: "BROWNIE",
  DOCINHO: "DOCINHO",
} as const;

export type CandyType = (typeof CandyType)[keyof typeof CandyType];
