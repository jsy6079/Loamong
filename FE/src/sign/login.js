import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../common/AuthContext";
import { Container, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({}); // 필드별 에러 메시지
  const [message, setMessage] = useState(""); // 로그인 실패 메시지
  const { login } = useContext(AuthContext);

  const navigate = useNavigate();

  const joinButton = () => {
    navigate("/join");
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    const newErrors = {};

    // 공백 검증
    if (!username.trim()) {
      newErrors.username = "아이디를 입력해주세요.";
    }
    if (!password.trim()) {
      newErrors.password = "비밀번호를 입력해주세요.";
    }

    // 에러가 있으면 서버 요청 중단
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/login",
        { username, password },
        { headers: { "Content-Type": "application/json" } }
      );

      const token = response.headers["authorization"]?.split(" ")[1];
      const serverUsername = decodeURIComponent(response.headers["username"]);

      if (token && serverUsername) {
        login(token, serverUsername);
        navigate("/");
      } else {
        setMessage("로그인 실패: 서버 오류.");
      }
    } catch (error) {
      setMessage(
        "아이디 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요."
      );
    }
  };

  return (
    <Container style={{ marginTop: "50px" }}>
      <Form onSubmit={handleLogin}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>아이디</Form.Label>
          <Form.Control
            type="text"
            placeholder="아이디를 입력해주세요."
            value={username}
            onChange={(e) => {
              setUsername(e.target.value);
              setErrors((prev) => ({ ...prev, username: "" })); // 입력 시 에러 메시지 초기화
            }}
            isInvalid={!!errors.username}
          />
          <Form.Text className="text-danger">{errors.username}</Form.Text>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control
            type="password"
            placeholder="비밀번호를 입력해주세요."
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setErrors((prev) => ({ ...prev, password: "" })); // 입력 시 에러 메시지 초기화
            }}
            isInvalid={!!errors.password}
          />
          <Form.Text className="text-danger">{errors.password}</Form.Text>
        </Form.Group>

        <Button variant="primary" type="submit" style={{ marginRight: "10px" }}>
          로그인
        </Button>
        <Button variant="dark" onClick={joinButton}>
          회원가입
        </Button>
      </Form>

      {/* 전체적인 로그인 실패 메시지 */}
      {message && (
        <Alert variant="danger" style={{ marginTop: "20px" }}>
          {message}
        </Alert>
      )}
    </Container>
  );
}

export default Login;
