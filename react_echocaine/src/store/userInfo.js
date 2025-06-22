import { createSlice } from "@reduxjs/toolkit";

export const userInfoSlice = createSlice({
  name: "userInfo",
  initialState: {
    info: {
      active: false,
      email: "",
      roles: [],
      username: "",
    },
  },
  reducers: {
    setUserInfo: (state, actions) => {
      state.info = actions.payload;
    },
  },
});

export const { setUserInfo } = userInfoSlice.actions;
export default userInfoSlice.reducer;
export const selectUserInfo = (state) => state.userInfo.info;
