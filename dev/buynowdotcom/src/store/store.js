import {configureStore} from "@reduxjs/toolkit"
import searchReducer from "../store/features/searchSlice"


export const store = configureStore({
    reducer: {
        search: searchReducer
    }
})