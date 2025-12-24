import { z } from "zod";
export interface IRegister {
  fullName: string;
  email: string;
  password: string;
}

export interface Ilogin {
  email: string;
  password: string;
}

export interface IloginResponse {
  email: string;
  token: string;
}

export const loginSchema = z.object({
  email: z.string().email({ message: "Invalid email" }),
  password: z
    .string()
    .min(1, { message: "The password must have at least 1 character" }),
});

export type loginData = z.infer<typeof loginSchema>;
