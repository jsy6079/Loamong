import React, { createContext, useState, useEffect } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// Context 생성
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);
  const [username, setUsername] = useState(null); // 사용자 이름 상태 추가

  // 컴포넌트가 마운트될 때 세션 스토리지에서 토큰과 사용자 이름 불러오기
  useEffect(() => {
    const storedToken = sessionStorage.getItem("jwtToken");
    const storedUsername = sessionStorage.getItem("username");
    if (storedToken) {
      setToken(storedToken);
    }
    if (storedUsername) {
      setUsername(storedUsername);
    }
  }, []);

  const login = (newToken, newUsername) => {
    sessionStorage.setItem("jwtToken", newToken); // sessionStorage에 토큰 저장
    sessionStorage.setItem("username", newUsername); // sessionStorage에 사용자 이름 저장
    setToken(newToken);
    setUsername(newUsername);

    toast.success("환영합니다 " + newUsername + "님 😀");
  };

  const logout = () => {
    sessionStorage.removeItem("jwtToken"); // 토큰 삭제
    sessionStorage.removeItem("username"); // 사용자 이름 삭제
    setToken(null);
    setUsername(null);

    // 팝업 메시지 표시
    toast.success("로그아웃되었습니다.");
  };

  // 올바른 위치에 return 추가
  return (
    <>
      {/* Toast 메시지 표시 컨테이너 */}
      <ToastContainer position="top-right" autoClose={2000} />
      <AuthContext.Provider value={{ token, username, login, logout }}>
        {children}
      </AuthContext.Provider>
    </>
  );
};
