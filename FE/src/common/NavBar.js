import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Nav, Navbar, Form, Button } from "react-bootstrap";

function NavBar() {
  const [searchCharacter, setSearchCharacter] = useState("");
  const navigate = useNavigate();

  // 검색 버튼 클릭 시 해당 캐릭터의 프로필 페이지로 이동
  const handleSearch = (e) => {
    e.preventDefault();
    if (searchCharacter.trim() !== "") {
      navigate(`/profile/${searchCharacter}`);
    }
  };

  const loaMong = () => {
    navigate("/");
  };

  return (
    <Navbar bg="dark" expand="lg" data-bs-theme="dark">
      <Container>
        <Navbar.Brand onClick={loaMong} style={{ cursor: "pointer" }}>
          LoaMong
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto"></Nav>
          {/* 폼을 Navbar 우측에 배치 */}
          <Form
            className="d-flex align-items-center"
            style={{ width: "400px" }}
            onSubmit={handleSearch}
          >
            <Form.Control
              type="text"
              placeholder="캐릭터 이름을 입력해주세요"
              className="me-2"
              value={searchCharacter}
              onChange={(e) => setSearchCharacter(e.target.value)}
            />
            <Button type="submit" variant="light" style={{ width: "70px" }}>
              검색
            </Button>
          </Form>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;
