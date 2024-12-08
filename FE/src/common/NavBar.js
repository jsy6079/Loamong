import React, { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Nav, Navbar, Form, Button } from "react-bootstrap";
import look from "../asset/look.png";
import logoW from "../asset/logoW.png";
import { AuthContext } from "../common/AuthContext";

function NavBar() {
  const { token, username, logout } = useContext(AuthContext);
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

  const loginButton = () => {
    navigate("/login");
  };

  return (
    <Navbar bg="dark" expand="lg" data-bs-theme="dark">
      <Container>
        {/* 로고 */}
        <Navbar.Brand
          onClick={loaMong}
          style={{
            cursor: "pointer",
            display: "flex",
            alignItems: "center",
            marginRight: "20px", // 로고와 검색창 사이 간격
          }}
        >
          <img
            src={logoW}
            style={{
              height: "45px", // 원하는 높이
              width: "auto", // 비율 유지
            }}
            alt="logo"
          />
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto"></Nav>
          {/* 검색창 */}
          <Form
            className="d-flex align-items-center"
            style={{
              width: "400px",
              marginRight: "20px", // 검색창과 로그인 버튼 사이 간격
            }}
            onSubmit={handleSearch}
          >
            <Form.Control
              type="text"
              placeholder="캐릭터 이름을 입력해주세요"
              className="me-2"
              value={searchCharacter}
              onChange={(e) => setSearchCharacter(e.target.value)}
            />
            <Button
              type="submit"
              variant="light"
              style={{
                width: "50px",
                height: "40px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                padding: 0,
              }}
            >
              <img
                src={look}
                alt="look"
                style={{
                  display: "block",
                  width: "90%",
                  height: "90%",
                }}
              />
            </Button>
          </Form>
        </Navbar.Collapse>
        {/* 로그인 버튼 */}

        {token ? (
          <Button
            variant="secondary"
            style={{ marginLeft: "auto" }}
            onClick={logout}
          >
            로그아웃
          </Button>
        ) : (
          <Button
            variant="secondary"
            style={{ marginLeft: "auto" }}
            onClick={loginButton}
          >
            로그인
          </Button>
        )}
      </Container>
    </Navbar>
  );
}

export default NavBar;
