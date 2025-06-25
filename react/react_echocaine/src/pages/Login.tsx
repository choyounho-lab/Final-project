import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { v4 } from 'uuid';
import { getCurrentUser, setCurrentUser } from '../helper/storage';
import { setUserInfo } from '../store/userInfo';
import { osName } from 'react-device-detect';
import axios from 'axios';
import './Login.css';

const Login = () => {
    const [loginPage, setLoginPage] = useState(true);
    const closeLoginPage = () => {
        setLoginPage(!loginPage);
    };

    const dispatch = useDispatch();

    const [isLoading, setIsLoading] = useState(false);
    const [loginData, setLoginData] = useState({
        username: '',
        password: '',
        deviceInfo: {
            deviceId: v4(),
            deviceType: '',
            notificationToken: v4(),
        },
    });

    const [agreeTerms, setAgreeTerms] = useState(false);
    const [agreePrivacy, setAgreePrivacy] = useState(false);
    const [agreeMarketing, setAgreeMarketing] = useState(false);

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginData({ ...loginData, [e.target.id]: e.target.value });
    };

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setIsLoading(true);

        axios
            .post('http://localhost:8000/api/auth/login', loginData)
            .then((res) => {
                if (res.data) {
                    axios
                        .get('http://localhost:8000/api/user/me', {
                            headers: {
                                Authorization: `${res.data.tokenType}${res.data.accessToken}`,
                            },
                        })
                        .then((result) => {
                            setCurrentUser(res.data);
                            dispatch(setUserInfo(result.data));
                        })
                        .finally(() => setIsLoading(false));
                }
            });
    };

    useEffect(() => {
        getCurrentUser();
        let device = '';
        switch (osName) {
            case 'Windows':
                device = 'DEVICE_TYPE_WINDOWS';
                break;
            case 'Mac OS':
                device = 'DEVICE_TYPE_MACOS';
                break;
            case 'Android':
                device = 'DEVICE_TYPE_ANDROID';
                break;
            case 'iOS':
                device = 'DEVICE_TYPE_IOS';
                break;
            default:
                device = 'OTHER';
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
                        position: 'absolute',
                        width: '100%',
                        height: '100%',
                        right: 0,
                        top: 0,
                    }}
                >
                    <div className="section">
                        <div className="container mx-auto">
                            <div className="row full-height justify-content-center">
                                <div className="col-12 text-center align-self-center py-5">
                                    <div className="section pb-5 pt-5 pt-sm-2 text-center">
                                        <input
                                            className="checkbox"
                                            type="checkbox"
                                            id="reg-log"
                                            name="reg-log"
                                        />
                                        <label htmlFor="reg-log"></label>

                                        <div className="card-3d-wrap mx-auto">
                                            <div className="card-3d-wrapper">
                                                {/* 로그인 카드 */}
                                                <div
                                                    className="card-front"
                                                    style={{
                                                        position: 'relative',
                                                    }}
                                                >
                                                    <button
                                                        className="close-btn"
                                                        onClick={closeLoginPage}
                                                    >
                                                        ✖
                                                    </button>
                                                    <div className="center-wrap">
                                                        <div className="section text-center">
                                                            <h4 className="mb-4 pb-3">
                                                                EchoCaine
                                                            </h4>
                                                            <form
                                                                onSubmit={
                                                                    onSubmit
                                                                }
                                                            >
                                                                <div className="form-group">
                                                                    <input
                                                                        type="email"
                                                                        id="username"
                                                                        className="form-style"
                                                                        placeholder="이메일"
                                                                        autoComplete="off"
                                                                        onChange={
                                                                            onChange
                                                                        }
                                                                        required
                                                                    />
                                                                    <i className="input-icon uil uil-at"></i>
                                                                </div>
                                                                <div className="form-group mt-2">
                                                                    <input
                                                                        type="password"
                                                                        id="password"
                                                                        className="form-style"
                                                                        placeholder="비밀번호"
                                                                        autoComplete="off"
                                                                        onChange={
                                                                            onChange
                                                                        }
                                                                        required
                                                                    />
                                                                    <i className="input-icon uil uil-lock-alt"></i>
                                                                </div>
                                                                <button
                                                                    type="submit"
                                                                    className="btn mt-4"
                                                                    disabled={
                                                                        isLoading
                                                                    }
                                                                >
                                                                    로그인
                                                                </button>
                                                            </form>
                                                            <p className="mb-0 mt-4 text-center">
                                                                <a
                                                                    href="#0"
                                                                    className="link"
                                                                >
                                                                    비밀번호를
                                                                    잊으셨나요?
                                                                </a>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>

                                                {/* 회원가입 카드 */}
                                                <div className="card-back">
                                                    <div className="center-wrap">
                                                        <div className="section text-center">
                                                            <h4 className="mb-4 pb-3">
                                                                EchoCaine
                                                            </h4>
                                                            <div className="form-group">
                                                                <input
                                                                    type="text"
                                                                    className="form-style"
                                                                    placeholder="이름"
                                                                    id="logname"
                                                                    autoComplete="off"
                                                                />
                                                                <i className="input-icon uil uil-user"></i>
                                                            </div>
                                                            <div className="form-group mt-2">
                                                                <input
                                                                    type="email"
                                                                    className="form-style"
                                                                    placeholder="이메일"
                                                                    id="signupEmail"
                                                                    autoComplete="off"
                                                                />
                                                                <i className="input-icon uil uil-at"></i>
                                                            </div>
                                                            <div className="form-group mt-2">
                                                                <input
                                                                    type="password"
                                                                    className="form-style"
                                                                    placeholder="비밀번호"
                                                                    id="signupPass"
                                                                    autoComplete="off"
                                                                />
                                                                <i className="input-icon uil uil-lock-alt"></i>
                                                                <div className="form-group mt-2">
                                                                    <input
                                                                        type="password"
                                                                        className="form-style"
                                                                        placeholder="비밀번호 확인"
                                                                        id="signupPass"
                                                                        autoComplete="off"
                                                                    />
                                                                    <i className="input-icon uil uil-lock-alt"></i>
                                                                </div>
                                                            </div>

                                                            {/* 약관 동의 */}
                                                            <div
                                                                className="form-group mt-2"
                                                                style={{
                                                                    textAlign:
                                                                        'left',
                                                                }}
                                                            >
                                                                <label>
                                                                    <input
                                                                        type="checkbox"
                                                                        checked={
                                                                            agreeTerms
                                                                        }
                                                                        onChange={() =>
                                                                            setAgreeTerms(
                                                                                !agreeTerms
                                                                            )
                                                                        }
                                                                    />{' '}
                                                                    [필수]{' '}
                                                                    <a
                                                                        href="/terms"
                                                                        target="_blank"
                                                                    >
                                                                        이용약관
                                                                    </a>
                                                                    에
                                                                    동의합니다
                                                                </label>
                                                                <br />
                                                                <label>
                                                                    <input
                                                                        type="checkbox"
                                                                        checked={
                                                                            agreePrivacy
                                                                        }
                                                                        onChange={() =>
                                                                            setAgreePrivacy(
                                                                                !agreePrivacy
                                                                            )
                                                                        }
                                                                    />{' '}
                                                                    [필수]{' '}
                                                                    <a
                                                                        href="/privacy"
                                                                        target="_blank"
                                                                    >
                                                                        개인정보처리방침
                                                                    </a>
                                                                    에
                                                                    동의합니다
                                                                </label>
                                                                <br />
                                                                <label>
                                                                    <input
                                                                        type="checkbox"
                                                                        checked={
                                                                            agreeMarketing
                                                                        }
                                                                        onChange={() =>
                                                                            setAgreeMarketing(
                                                                                !agreeMarketing
                                                                            )
                                                                        }
                                                                    />{' '}
                                                                    [선택]
                                                                    마케팅
                                                                    수신에
                                                                    동의합니다
                                                                </label>
                                                            </div>

                                                            <a
                                                                href="#"
                                                                className="btn mt-4"
                                                                onClick={() => {
                                                                    if (
                                                                        !agreeTerms ||
                                                                        !agreePrivacy
                                                                    ) {
                                                                        alert(
                                                                            '필수 약관에 모두 동의해야 회원가입이 가능합니다.'
                                                                        );
                                                                        return;
                                                                    }

                                                                    const signupPayload =
                                                                        {
                                                                            name: '',
                                                                            email: '',
                                                                            password:
                                                                                '',
                                                                            agreeTerms,
                                                                            agreePrivacy,
                                                                            agreeMarketing,
                                                                        };

                                                                    console.log(
                                                                        '회원가입 데이터:',
                                                                        signupPayload
                                                                    );
                                                                    // axios.post("/api/auth/signup", signupPayload) 하면 됨
                                                                }}
                                                            >
                                                                회원가입
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* 끝 */}
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
