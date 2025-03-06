import { api } from "./api";

export const getDistinctProductsByName = async () => {
    try {
        const response = await api.get("/products/distinct");

        console.log("Poprawna odpowiedź:", response.data); 
        return response.data;
    } catch (error) {
        if (error.response) {
            console.log("Błąd odpowiedzi z backendu:", error.response); 
        } else {
            console.log("Błąd sieci lub inny:", error.message); 
        }
        throw error; 
    }
};
