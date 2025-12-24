import z from "zod";

export interface Icandy {
  id: number;
  name: string;
  price: number;
  description: string;
  type: CandyType;
  imageUrl?: string;
}

export interface IcandyRequest {
  name: string;
  price: number;
  description: string;
  type: CandyType;
}

export const CandyType = {
  CAKE: "CAKE",
  MUFFIN: "MUFFIN",
  BROWNIE: "BROWNIE",
  DOCINHO: "DOCINHO",
} as const;

export type CandyType = (typeof CandyType)[keyof typeof CandyType];

export const candySchema = z.object({
  name: z.string(),
  price: z.number().min(0, { message: "The price must be at least 0" }),
  description: z.string(),
  type: z.enum(["CAKE", "MUFFIN", "BROWNIE", "DOCINHO"]),
});

export type candyData = z.infer<typeof candySchema>;
