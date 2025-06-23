export const setCurrentUser = (user) => {
  try {
    if (user) {
      localStorage.setItem("user", JSON.stringify(user));
    } else {
      localStorage.removeItem("user");
    }
  } catch (error) {
    console.log("스토리지 저장 오류", error);
  }
};

export const removeCurrnetUser = () => {
  localStorage.removeItem("user");
};

export const getCurrentUser = () => {
  let user = null;
  try {
    user =
      localStorage.getItem("user") != null
        ? JSON.parse(localStorage.getItem("user"))
        : null;
  } catch (error) {
    console.log(error);
    user = null;
  }
  return user;
};
