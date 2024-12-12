import React, { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Nav, Navbar, Form, Button } from "react-bootstrap";
import look from "../asset/look.png";
import logoW from "../asset/logoW.png";
import { AuthContext } from "../common/AuthContext";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

function NavBar() {
  const { token, username, logout } = useContext(AuthContext);
  const [searchCharacter, setSearchCharacter] = useState("");
  const navigate = useNavigate();

  // JWT에서 username 추출
  const getUsernameFromToken = (token) => {
    try {
      const decoded = jwtDecode(token); // 토큰 디코드
      return decoded.username; // 디코드된 payload에서 username 추출
    } catch (error) {
      console.error("토큰 디코딩 실패:", error);
      return null;
    }
  };

  const extractedUsername = token ? getUsernameFromToken(token) : null;

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

  // 아임포트 결제 요청

  const handlePayment = () => {
    const { IMP } = window; // 아임포트 전역 객체
    IMP.init("imp35813801"); // 가맹점 식별 코드 입력

    const data = {
      pg: "html5_inicis", // PG사
      pay_method: "card", // 결제 방식
      merchant_uid: `order_${new Date().getTime()}`, // 고유 주문번호
      name: "LoaMong 프리미엄 서비스", // 상품 이름
      amount: 100, // 결제 금액 (원)
      buyer_email: `${extractedUsername}@example.com`, // 구매자 이메일
      buyer_name: extractedUsername, // 구매자 이름
    };

    IMP.request_pay(data, (response) => {
      console.log("결제 요청 데이터:", data); // 결제 요청 데이터를 콘솔에 출력
      if (response.success) {
        console.log("결제 성공 응답:", response); // 결제 성공 시 응답 데이터를 출력
        // 결제 성공 시 서버로 검증 요청
        axios
          .post(
            "http://localhost:8080/api/verify",
            { imp_uid: response.imp_uid, username: extractedUsername }, // 요청 본문
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`, // JWT 토큰
              },
            }
          )
          .then((res) => {
            const result = res.data;
            if (result === "결제가 성공적으로 검증되었습니다.") {
              alert("결제가 완료되었습니다!");
              navigate("/");
            } else {
              alert("결제 검증 실패: " + result);
            }
          })
          .catch((error) => {
            console.error("결제 검증 요청 실패:", error);
            alert("결제 검증 요청 중 문제가 발생했습니다.");
          });
      } else {
        // 결제 실패 처리
        alert(`결제 실패: ${response.error_msg}`);
      }
    });
  };

  return (
    <Navbar bg="dark" expand="lg" data-bs-theme="dark">
      <Container>
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

        {token ? (
          <>
            <Button
              variant="secondary"
              style={{ marginLeft: "auto" }}
              onClick={logout}
            >
              로그아웃
            </Button>
            <Button
              variant="danger"
              style={{ marginLeft: "10px" }}
              onClick={handlePayment}
            >
              돈 내놔
            </Button>
          </>
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
