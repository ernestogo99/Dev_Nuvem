import type { Ilogin, IloginResponse, IRegister } from "../interfaces";
import { api } from "./axiosconfig/config";

const signUp = async (data: IRegister): Promise<void | Error> => {
  try {
    await api.post("/api/auth/signup", data);
  } catch (error: any) {
    const message = error.response?.data?.message || "Error during register.";
    return new Error(message);
  }
};

const login = async (loginData: Ilogin): Promise<IloginResponse | Error> => {
  try {
    const { data } = await api.post("/api/auth/login", loginData);
    return data;
  } catch (error: any) {
    const message = error.response?.data?.message || "Invalid credentials.";
    return new Error(message);
  }
};

export const authService = {
  signUp,
  login,
};
