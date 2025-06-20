import { useState } from "react";
import Home from "./HomeSave";
import { Link } from "react-router-dom";

const Login = () => {
  const [loginPage, setLoginPage] = useState(true);
  const closeLoginPage = () => {
    setLoginPage(!loginPage);
  };
  return (
    <div>
      {loginPage ? (
        <div
          className="bg-gray-400 w-full h-full"
          style={{
            position: "absolute",
            width: "100%",
            height: "100%",
            background: "rgba(0,0,0,0.8)",
            right: 0,
            top: 0,
          }}
        >
          <div
            style={{
              position: "relative",
              margin: "auto",
              width: "600px",
              height: "400px",
              background: "#fff",
              transform: "translateY(-50%)",
              top: "50%",
              borderRadius: "20px",
            }}
          >
            <button
              type="button"
              onClick={closeLoginPage}
              style={{
                position: "absolute",
                top: 10,
                right: 20,
                cursor: "pointer",
              }}
            >
              ✖
            </button>
            <div className="flex flex-col justify-center items-center h-full p-10 font-bold text-lg">
              <div className="flex w-90 h-10 items-center justify-between  ">
                <label htmlFor="userId" className="">
                  아 이 디
                </label>
                <input id="userId" type="text" className="border"></input>
              </div>
              <div className="flex w-90 h-10 items-center justify-between ">
                <label htmlFor="userPassword" className="">
                  비밀번호
                </label>
                <input id="userPassword" type="text" className="border"></input>
              </div>
              <div className="flex justify-between mt-5 text-lg font-bold w-90">
                <button type="button">아이디 찾기</button>
                <button type="button">비밀번호 찾기</button>
                <button type="button">회원가입</button>
              </div>
              <div className="text-2xl bg-gray-500 w-95 text-center text-white rounded-lg mt-5 h-13 flex items-center justify-center">
                로그인
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
};
export default Login;
