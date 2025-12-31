import type { Icandy, IcandyRequest } from "../interfaces";
import { api } from "./axiosconfig/config";

const getallCakes = async (): Promise<Icandy[] | Error> => {
  try {
    const { data } = await api.get("/candies/cakes");
    if (data) {
      return data;
    }
    return new Error("Error while trying to get cakes.");
  } catch (error: any) {
    const message =
      error.response?.data?.message || "Error while trying to get cakes.";
    return new Error(message);
  }
};

const getallMuffins = async (): Promise<Icandy[] | Error> => {
  try {
    const { data } = await api.get("/candies/muffins");
    if (data) {
      return data;
    }
    return new Error("Error while trying to get cakes.");
  } catch (error: any) {
    const message =
      error.response?.data?.message || "Error while trying to get muffins.";
    return new Error(message);
  }
};

const getallDocinhos = async (): Promise<Icandy[] | Error> => {
  try {
    const { data } = await api.get("/candies/docinhos");
    if (data) {
      return data;
    }
    return new Error("Error while trying to get cakes.");
  } catch (error: any) {
    const message =
      error.response?.data?.message ||
      "Error while trying to get cakes docinhoss.";
    return new Error(message);
  }
};

const getallBrownies = async (): Promise<Icandy[] | Error> => {
  try {
    const { data } = await api.get("/candies/brownies");
    if (data) {
      return data;
    }
    return new Error("Error while trying to get cakes.");
  } catch (error: any) {
    const message =
      error.response?.data?.message || "Error while trying to get brownies.";
    return new Error(message);
  }
};

const getallCandies = async (): Promise<Icandy[] | Error> => {
  try {
    const { data } = await api.get("/candies");
    if (data) {
      return data;
    }
    return new Error("Error while trying to get cakes.");
  } catch (error: any) {
    const message =
      error.response?.data?.message || "Error while trying to get all candies.";
    return new Error(message);
  }
};

const createCandy = async (
  candy: IcandyRequest,
  image: File
): Promise<Icandy | Error> => {
  try {
    const formData = new FormData();
    formData.append(
      "candy",
      new Blob([JSON.stringify(candy)], { type: "application/json" })
    );
    formData.append("image", image);

    const { data } = await api.post("/candies", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return data;
  } catch (error: any) {
    const message =
      error.response?.data?.message || "Error while trying to create candy.";
    return new Error(message);
  }
};

const getCandyById = async (id: number): Promise<Icandy | Error> => {
  try {
    const { data } = await api.get(`/candies/${id}`);
    if (data) {
      return data;
    }
    return new Error("Candy not found.");
  } catch (error: any) {
    const message = error.response?.data?.message || "Candy not found.";
    return new Error(message);
  }
};

const deleteCandy = async (id: number): Promise<void | Error> => {
  try {
    await api.delete(`/candies/${id}`);
  } catch (error: any) {
    const message = error.response?.data?.message || "Candy not found.";
    return new Error(message);
  }
};

const updateCandy = async (
  id: number,
  candy: IcandyRequest,
  image?: File
): Promise<Icandy | Error> => {
  try {
    const formData = new FormData();

    formData.append(
      "candy",
      new Blob([JSON.stringify(candy)], { type: "application/json" })
    );

    if (image) {
      formData.append("image", image);
    }

    const { data } = await api.put(`/candies/${id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return data;
  } catch (error: any) {
    const message =
      error.response?.data?.message || "Error while trying to update candy.";
    return new Error(message);
  }
};

export const candyService = {
  createCandy,
  deleteCandy,
  updateCandy,
  getCandyById,
  getallBrownies,
  getallCakes,
  getallCandies,
  getallDocinhos,
  getallMuffins,
};
