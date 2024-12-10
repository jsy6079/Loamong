import React, { useState } from "react";
import { Container, Form, Button, Alert, Row, Col } from "react-bootstrap";
import axios from "axios";

function Join() {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    nickname: "",
  });
  const [errors, setErrors] = useState({}); // 서버에서 받은 에러 메시지
  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
    setErrors({
      ...errors,
      [e.target.name]: "",
    });
  };

  const validateForm = () => {
    const newErrors = {};

    // 공백 확인
    if (!formData.username.trim()) {
      newErrors.username = "아이디를 입력해주세요.";
    } else if (formData.username.includes(" ")) {
      newErrors.username = "아이디에 공백이 포함될 수 없습니다.";
    }

    if (!formData.password.trim()) {
      newErrors.password = "비밀번호를 입력해주세요.";
    } else if (formData.password.includes(" ")) {
      newErrors.password = "비밀번호에 공백이 포함될 수 없습니다.";
    }

    if (!formData.nickname.trim()) {
      newErrors.nickname = "닉네임을 입력해주세요.";
    } else if (formData.nickname.includes(" ")) {
      newErrors.nickname = "닉네임에 공백이 포함될 수 없습니다.";
    }

    return newErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setSuccessMessage("");

    // 공백 검증
    const newErrors = validateForm();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/api/join",
        formData
      );
      setSuccessMessage(response.data.message); // 성공 메시지 설정
    } catch (error) {
      if (error.response && error.response.data) {
        setErrors(error.response.data); // 서버에서 받은 에러 메시지
      }
    }
  };

  return (
    <Container style={{ marginTop: "50px" }}>
      <Form onSubmit={handleSubmit}>
        <Row>
          <Col md={6}>
            <Form.Group>
              <Form.Label>아이디</Form.Label>
              <Form.Control
                name="username"
                value={formData.username}
                onChange={handleChange}
                placeholder="아이디를 입력하세요."
                isInvalid={!!errors.username}
              />
              <Form.Text className="text-danger">{errors.username}</Form.Text>
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group>
              <Form.Label>닉네임</Form.Label>
              <Form.Control
                name="nickname"
                value={formData.nickname}
                onChange={handleChange}
                placeholder="닉네임을 입력하세요."
                isInvalid={!!errors.nickname}
              />
              <Form.Text className="text-danger">{errors.nickname}</Form.Text>
            </Form.Group>
          </Col>
        </Row>

        {/* 비밀번호 입력 */}
        <Form.Group className="mt-3">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="비밀번호를 입력하세요."
            isInvalid={!!errors.password}
          />
          <Form.Text className="text-danger">{errors.password}</Form.Text>
        </Form.Group>

        {/* 가입 버튼 */}
        <Button type="submit" className="mt-3">
          가입하기
        </Button>
      </Form>

      {/* 성공 메시지 */}
      {successMessage && (
        <Alert variant="success" className="mt-3">
          {successMessage}
        </Alert>
      )}
    </Container>
  );
}

export default Join;
