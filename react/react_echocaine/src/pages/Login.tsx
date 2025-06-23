import { useEffect, useState } from "react";

import { useDispatch } from "react-redux";
import { v4 } from "uuid";
import { getCurrentUser, setCurrentUser } from "../helper/storage";
import { setUserInfo } from "../store/userInfo";
import { osName } from "react-device-detect";
import axios from "axios";

const Login = () => {
  const [loginPage, setLoginPage] = useState(true);
  const closeLoginPage = () => {
    setLoginPage(!loginPage);
  };

  const dispatch = useDispatch();

  const [isLoading, setIsLoading] = useState(false);
  const [loginData, setLoginData] = useState({
    username: "",
    password: "",
    deviceInfo: {
      deviceId: v4(),
      deviceType: "",
      notificationToken: v4(),
    },
  });

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLoginData({ ...loginData, [e.target.id]: e.target.value });
  };
  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    setIsLoading(true);
    e.preventDefault();
    console.log(loginData);
    const data = axios
      .post("http://localhost:8000/api/auth/login", loginData)
      .then((res) => {
        console.log(res.data);
        if (res.data) {
          axios
            .get("http://localhost:8000/api/user/me", {
              headers: {
                Authorization: `${res.data.tokenType}${res.data.accessToken}`,
              },
            })
            .then((result) => {
              console.log(result.data);
              console.log(res.data);
              // 토큰을 로컬스토리지에 저장
              setCurrentUser(res.data);
              // 사용자 정보는 store에 저장
              dispatch(setUserInfo(result.data));
            })
            .finally(() => setIsLoading(false));
        }
      });
  };
  useEffect(() => {
    getCurrentUser();
    console.log(v4());
    console.log(osName);
    let device = "";
    switch (osName) {
      case "Windows":
        device = "DEVICE_TYPE_WINDOWS";
        break;
      case "Max Os":
        device = "DEVICE_TYPE_MACOS";
        break;
      case "Android":
        device = "DEVICE_TYPE_ANDROID";
        break;
      case "iOS":
        device = "DEVICE_TYPE_IOS";
        break;
      default:
        device = "OTHER";
        break;
    }
    setLoginData({
      ...loginData,
      deviceInfo: {
        ...loginData.deviceInfo,
        deviceType: device,
      },
    });
  }, []);

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
            <form onSubmit={onSubmit} className="h-full">
              <div className="flex flex-col justify-center items-center h-full p-10 font-bold text-lg">
                <div className="flex w-90 h-10 items-center justify-between  ">
                  <label htmlFor="username" className="">
                    아 이 디
                  </label>
                  <input
                    id="username"
                    type="text"
                    className="border"
                    value={loginData.username}
                    onChange={onChange}
                  ></input>
                </div>
                <div className="flex w-90 h-10 items-center justify-between ">
                  <label htmlFor="password" className="">
                    비밀번호
                  </label>
                  <input
                    id="password"
                    type="text"
                    className="border"
                    value={loginData.password}
                    onChange={onChange}
                  ></input>
                </div>
                <div className="flex justify-between mt-5 text-lg font-bold w-90">
                  <button type="button">아이디 찾기</button>
                  <button type="button">비밀번호 찾기</button>
                  <button type="button">회원가입</button>
                </div>
                <button
                  type="submit"
                  disabled={isLoading}
                  className="text-2xl bg-gray-500 w-95 text-center text-white rounded-lg mt-5 h-13 flex items-center justify-center"
                >
                  로그인
                </button>
              </div>
            </form>
          </div>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
};
export default Login;
