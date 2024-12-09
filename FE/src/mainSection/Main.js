import React, { useEffect, useState, useContext } from "react";
import { Container, Row, Col, Badge, Card, Carousel } from "react-bootstrap";
import axios from "axios";
import cahos from "../asset/cahos.png";
import fild from "../asset/fild.png";
import noCahos from "../asset/noCahos.png";
import noFild from "../asset/noFild.png";

function Main() {
  const [isIandData, setIslandData] = useState([]);
  const [newsData, setNewsData] = useState([]);
  const [eventsData, setEventsData] = useState([]);
  const [fildBossData, setFildBossData] = useState([]);
  const [chaosGateData, setChaosGateData] = useState([]);

  const axiosData = async () => {
    try {
      const islandResponse = await axios.get(
        "http://localhost:8080/api/loa/calender/island"
      );
      const newsResponse = await axios.get(
        "http://localhost:8080/api/loa/news"
      );
      const eventsResponse = await axios.get(
        "http://localhost:8080/api/loa/events"
      );
      const fildBossResponse = await axios.get(
        "http://localhost:8080/api/loa/calender/fildBoss"
      );
      const chaosGateResponse = await axios.get(
        "http://localhost:8080/api/loa/calender/chaosGate"
      );

      setIslandData(islandResponse.data);
      setNewsData(newsResponse.data);
      setEventsData(eventsResponse.data);
      setFildBossData(fildBossResponse.data);
      setChaosGateData(chaosGateResponse.data);
    } catch (error) {
      console.log("데이터를 가져오지 못했습니다: " + error);
    }
  };

  useEffect(() => {
    axiosData();
  }, []);

  return (
    <>
      <Container style={{ marginTop: "50px" }}>
        {/* ----------------------캘린더 section ------------------------ */}
        <Row>
          <div
            style={{
              display: "flex",
              alignItems: "center", // 수직 정렬
              justifyContent: "space-between",
            }}
          >
            <h3 style={{ marginRight: "10px" }}>모험 섬🏝️</h3>
            <div
              style={{
                display: "flex",
                flexDirection: "column",
              }}
            >
              <div
                style={{
                  display: "flex",
                  gap: "20px",
                }}
              >
                {/* 필드보스 */}
                <div style={{ textAlign: "center" }}>
                  {fildBossData && fildBossData.length > 0 ? (
                    fildBossData.map((fildBoss, index) => (
                      <div key={index} style={{ marginBottom: "10px" }}>
                        <img
                          src={
                            fildBoss.TodayStartTimes.length >= 7 ? fild : noFild
                          }
                          style={{
                            width: "40px",
                            height: "40px",
                          }}
                        />
                        <h6 style={{ marginTop: "5px" }}>
                          <Badge
                            bg={
                              fildBoss.TodayStartTimes.length >= 7
                                ? "success"
                                : "secondary"
                            }
                          >
                            {fildBoss.TodayStartTimes.length >= 7
                              ? "필드보스"
                              : "자리비움"}
                          </Badge>
                        </h6>
                      </div>
                    ))
                  ) : (
                    <div>
                      <img
                        src={noFild}
                        style={{
                          width: "40px",
                          height: "40px",
                        }}
                      />
                      <h6 style={{ marginTop: "5px" }}>
                        <Badge bg="secondary">자리비움</Badge>
                      </h6>
                    </div>
                  )}
                </div>

                {/* 카오스게이트 */}
                <div style={{ textAlign: "center" }}>
                  {chaosGateData && chaosGateData.length > 0 ? (
                    chaosGateData.map((chaosGate, index) => (
                      <div key={index} style={{ marginBottom: "10px" }}>
                        <img
                          src={
                            chaosGate.TodayStartTimes.length >= 7
                              ? cahos
                              : noCahos
                          }
                          style={{
                            width: "40px",
                            height: "40px",
                          }}
                        />
                        <h6 style={{ marginTop: "5px" }}>
                          <Badge
                            bg={
                              chaosGate.TodayStartTimes.length >= 7
                                ? "primary"
                                : "secondary"
                            }
                          >
                            {chaosGate.TodayStartTimes.length >= 7
                              ? "카오스게이트"
                              : "자리비움"}
                          </Badge>
                        </h6>
                      </div>
                    ))
                  ) : (
                    <div>
                      <img
                        src={noCahos}
                        style={{
                          width: "40px",
                          height: "40px",
                        }}
                      />
                      <h6 style={{ marginTop: "5px" }}>
                        <Badge bg="secondary">자리비움</Badge>
                      </h6>
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
          {/* 모험 섬 */}
          <div className="d-flex flex-wrap">
            {isIandData.map((island, index) => (
              <Card
                key={index}
                style={{
                  width: "12rem",
                  margin: "10px",
                  border: "1px solid #ddd",
                  borderRadius: "10px",
                  overflow: "hidden",
                }}
              >
                <div
                  style={{
                    height: "100px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    backgroundColor: "#f6f6f6",
                  }}
                >
                  <img
                    src={island.ContentsIcon}
                    alt={island.ContentsName}
                    style={{
                      maxWidth: "100%",
                      maxHeight: "100%",
                      objectFit: "contain",
                    }}
                  />
                </div>

                <Card.Body style={{ padding: "5px" }}>
                  <Card.Title
                    style={{ fontSize: "0.9rem", textAlign: "center" }}
                  >
                    <Badge bg="danger">{island.ContentsName}</Badge>
                  </Card.Title>

                  <div className="d-flex flex-wrap mt-2">
                    {island.RewardItems[0].Items.map((reward, idx) => (
                      <div
                        key={idx}
                        style={{
                          width: "28px",
                          textAlign: "center",
                          marginBottom: "5px",
                          marginRight: "5px",
                        }}
                      >
                        <img
                          src={reward.Icon}
                          alt={reward.Name}
                          style={{
                            width: "30px",
                            height: "30px",
                            objectFit: "contain",
                            borderRadius: "5px",
                          }}
                        />
                      </div>
                    ))}
                  </div>
                </Card.Body>
              </Card>
            ))}
          </div>
        </Row>
      </Container>
      {/* ----------------------공지사항 section ------------------------ */}
      <Container style={{ marginTop: "50px" }}>
        <Row>
          <Col>
            <h3>공지사항📢</h3>
            <hr />
            {newsData.map((news, index) => (
              <div key={index}>
                <Badge bg="primary" style={{ marginRight: "10px" }}>
                  {news.Type}
                </Badge>
                <span style={{ marginRight: "10px" }}>
                  <a
                    href={news.Link}
                    target="_blank"
                    rel="noopener noreferrer"
                    style={{ textDecoration: "none", color: "#007bff" }}
                  >
                    {news.Title}
                  </a>
                </span>
                <hr />
              </div>
            ))}
          </Col>
        </Row>
      </Container>
      {/* ----------------------이벤트 section ------------------------ */}
      <Container style={{ marginTop: "50px" }}>
        <Row>
          <Col xs={12} style={{ marginBottom: "10px" }}>
            <h3>이벤트🎉</h3>
          </Col>
        </Row>
        {/* 카드 가로 배치 */}
        <Row>
          {eventsData.map((events, index) => (
            <Col
              key={index}
              xs={12}
              sm={6}
              md={4}
              lg={3}
              style={{ marginBottom: "20px" }}
            >
              <Card>
                <Card.Img
                  variant="top"
                  src={events.Thumbnail}
                  style={{
                    width: "100%",
                    height: "100%",
                    objectFit: "cover",
                  }}
                />
                <Card.Body>
                  <a
                    href={events.Link}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    <p>{events.Title}</p>
                  </a>
                  <Card.Text>
                    {new Date(events.StartDate).toLocaleDateString("ko-KR")} ~{" "}
                    {new Date(events.EndDate).toLocaleDateString("ko-KR")}
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </>
  );
}

export default Main;
