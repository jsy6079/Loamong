import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../common/AuthContext";
import { Container, Form, Button, Alert } from "react-bootstrap";
import axios from "axios";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const { login } = useContext(AuthContext);

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
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
        setMessage("로그인 실패: 토큰이 없습니다.");
      }
    } catch (error) {
      setMessage(
        "로그인 실패: " + (error.response?.data?.message || error.message)
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
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control
            type="password"
            placeholder="비밀번호를 입력해주세요."
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="아이디 저장" />
        </Form.Group>

        <Button variant="primary" type="submit">
          로그인
        </Button>
      </Form>

      {message && <Alert style={{ marginTop: "20px" }}>{message}</Alert>}
    </Container>
  );
}

export default Login;
