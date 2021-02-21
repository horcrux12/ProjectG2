import { combineReducers } from "redux";
import AuthReducer from "./auth";

const reducers = combineReducers({
    AuthReducer : AuthReducer,
})

export default reducers