import { useEffect, useState } from "react";

import { useDispatch } from "react-redux";
import { v4 } from "uuid";
import { getCurrentUser, setCurrentUser } from "../helper/storage";
import { setUserInfo } from "../store/userInfo";
import { osName } from "react-device-detect";
import axios from "axios";
import "../assets/css/Login.css";

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
      {loginPage && (
        <div
          className="bg-overlay"
          style={{
            position: "absolute",
            width: "100%",
            height: "100%",
            right: 0,
            top: 0,
          }}
        >
          <a
            href="https://front.codes/"
            className="logo"
            target="_blank"
            rel="noreferrer"
          ></a>
          <div className="section">
            <div className="container">
              <div className="row full-height justify-content-center">
                <div className="col-12 text-center align-self-center py-5">
                  <div className="section pb-5 pt-5 pt-sm-2 text-center">
                    <h6 className="mb-0 pb-3"></h6>
                    <input
                      className="checkbox"
                      type="checkbox"
                      id="reg-log"
                      name="reg-log"
                    />
                    <label htmlFor="reg-log"></label>
                    <div className="card-3d-wrap mx-auto">
                      <div className="card-3d-wrapper">
                        <div className="card-front">
                          <div className="center-wrap">
                            <div className="section text-center">
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
                              <h4 className="mb-4 pb-3">로그인</h4>
                              <div className="form-group">
                                <input
                                  type="email"
                                  name="logemail"
                                  className="form-style"
                                  placeholder="이메일을 입력하세요"
                                  id="logemail"
                                  autoComplete="off"
                                />
                                <i className="input-icon uil uil-at"></i>
                              </div>
                              <div className="form-group mt-2">
                                <input
                                  type="password"
                                  name="logpass"
                                  className="form-style"
                                  placeholder="비밀번호를 입력하세요"
                                  id="logpass"
                                  autoComplete="off"
                                />
                                <i className="input-icon uil uil-lock-alt"></i>
                              </div>
                              <a href="#" className="btn mt-4">
                                로그인
                              </a>
                              <p className="mb-0 mt-4 text-center">
                                <a href="#0" className="link">
                                  비밀번호를 잊으셨나요?
                                </a>
                              </p>
                            </div>
                          </div>
                        </div>
                        <div className="card-back">
                          <div className="center-wrap">
                            <div className="section text-center">
                              <h4 className="mb-4 pb-3">회원가입</h4>
                              <div className="form-group">
                                <input
                                  type="text"
                                  name="logname"
                                  className="form-style"
                                  placeholder="Your Full Name"
                                  id="logname"
                                  autoComplete="off"
                                />
                                <i className="input-icon uil uil-user"></i>
                              </div>
                              <div className="form-group mt-2">
                                <input
                                  type="email"
                                  name="logemail"
                                  className="form-style"
                                  placeholder="Your Email"
                                  id="logemail"
                                  autoComplete="off"
                                />
                                <i className="input-icon uil uil-at"></i>
                              </div>
                              <div className="form-group mt-2">
                                <input
                                  type="password"
                                  name="logpass"
                                  className="form-style"
                                  placeholder="Your Password"
                                  id="logpass"
                                  autoComplete="off"
                                />
                                <i className="input-icon uil uil-lock-alt"></i>
                              </div>

                              <a href="#" className="btn mt-4">
                                회원가입
                              </a>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default Login;
