import React, { useState } from "react";
import { Container, Button, Col, Form, Row, Alert } from "react-bootstrap";
import axios from "axios";

function Join() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");

  const joinSubmit = async () => {
    try {
      const data = {
        username: username,
        password: password,
        nickname: nickname,
      };

      const response = await axios.post("http://localhost:8080/api/join", data);

      console.log("성공");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Container style={{ marginTop: "50px" }}>
      <Form>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridId">
            <Form.Label>아이디</Form.Label>
            <Form.Control
              placeholder="아이디를 입력해주세요."
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridNickName">
            <Form.Label>닉네임</Form.Label>
            <Form.Control
              placeholder="닉네임을 입력해주세요."
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Form.Group className="mb-3" controlId="formGridPassword">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>

        <Form.Group className="mb-3" id="formGridCheckbox">
          <Form.Check type="checkbox" label="Check me out" />
        </Form.Group>

        <Button variant="primary" onClick={joinSubmit}>
          회원가입
        </Button>
      </Form>
    </Container>
  );
}

export default Join;
