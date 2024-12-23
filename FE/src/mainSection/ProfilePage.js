import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { Col, Container, Row, Tab, Tabs, Badge, Button } from "react-bootstrap";

function ProfilePage() {
  const { characterName } = useParams();
  const [profileData, setProfileData] = useState(null);
  const [collectionData, setCollectionData] = useState([]);

  const fetchProfileData = async () => {
    try {
      const profileResponse = await axios.get(
        `http://localhost:8080/api/loa/character/profile/${characterName}`
      );
      const collectionResponse = await axios.get(
        `http://localhost:8080/api/loa/character/collection/${characterName}`
      );
      setProfileData(profileResponse.data);
      setCollectionData(collectionResponse.data || []); // 빈 배열 기본값
    } catch (error) {
      console.log("프로필 데이터를 가져오지 못했습니다:", error);
      setProfileData(null);
      setCollectionData([]); // 안전하게 초기화
    }
  };

  function getBadgeColor(serverName) {
    switch (serverName) {
      case "루페온":
        return "primary"; // 보라색
      case "실리안":
        return "secondary"; // 주황색
      case "아만":
        return "success"; // 주황색
      case "카마인":
        return "danger"; // 주황색
      case "카제로스":
        return "warning"; // 주황색
      case "아브렐슈드":
        return "warning"; // 주황색
      case "카단":
        return "dark"; // 주황색
      case "니나브":
        return "info"; // 주황색
    }
  }

  function getTextColor(serverName) {
    return serverName === "아브렐슈드" ? "black" : "white";
  }

  const getMaxPoint = (type) => {
    if (!Array.isArray(collectionData)) {
      console.error("collectionData is not an array:", collectionData);
      return "N/A";
    }
    const collection = collectionData.find((item) => item.Type === type);
    return collection ? collection.Point : "N/A";
  };

  useEffect(() => {
    fetchProfileData();
  }, [characterName]);

  return (
    <Container style={{ marginTop: "50px" }}>
      <Row>
        <Col xs={6} md={4}>
          <div>
            {profileData ? (
              <div>
                <img
                  src={profileData.CharacterImage}
                  alt={profileData.CharacterName}
                  style={{
                    maxWidth: "100%",
                    height: "auto",
                    marginBottom: "10px",
                  }}
                />
                <h4>
                  <Badge
                    bg={getBadgeColor(profileData.ServerName)}
                    text={getTextColor(profileData.ServerName)} // 텍스트 색상 지정
                    style={{ marginRight: "10px" }}
                  >
                    {profileData.ServerName}
                  </Badge>
                  {profileData.CharacterName} [ {profileData.ItemAvgLevel} ]
                </h4>
              </div>
            ) : (
              <p></p>
            )}
          </div>
        </Col>
        <Col xs={12} md={8}>
          <Tabs
            defaultActiveKey="ability"
            id="fill-tab-example"
            className="mb-3"
            fill
          >
            <Tab eventKey="ability" title="세팅">
              <div>
                {profileData ? (
                  <div>
                    <h3>스탯 정보</h3>
                    {profileData.Stats.map((stat, index) => (
                      <div key={index}>
                        <strong>{stat.Type}:</strong> {stat.Value}
                      </div>
                    ))}
                  </div>
                ) : (
                  <p>해당 유저는 존재하지않습니다.</p>
                )}
              </div>
            </Tab>
            <Tab eventKey="equipment" title="스킬">
              스킬
            </Tab>

            <Tab eventKey="collectibles" title="내실">
              <div
                style={{
                  display: "flex",
                  flexWrap: "wrap",
                  gap: "60px", // 아이콘 간 간격 조정
                  justifyContent: "center", // 중앙 정렬
                  alignItems: "center", // 세로축 정렬
                  marginTop: "10px",
                }}
              >
                <div
                  style={{
                    display: "flex",
                    flexWrap: "wrap",
                    gap: "50px", // 아이콘 간 간격
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  {/* 섬마 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-234px -24px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("섬의 마음")}
                      </p>
                    </Button>
                  </div>

                  {/* 거심 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      variant="light"
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-205px -172px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("거인의 심장")}
                      </p>
                    </Button>
                  </div>

                  {/* 오페 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-235px -70px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("오르페우스의 별")}
                      </p>
                    </Button>
                  </div>

                  {/* 미술품 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-183px -172px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("위대한 미술품")}
                      </p>
                    </Button>
                  </div>

                  {/* 오르골 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-1px -215px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("기억의 오르골")}
                      </p>
                    </Button>
                  </div>

                  {/* 해도 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-1px -215px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("크림스네일의 해도")}
                      </p>
                    </Button>
                  </div>

                  {/* 모코코 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-234px -92px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("모코코 씨앗")}
                      </p>
                    </Button>
                  </div>

                  {/* 이그 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-236px -1px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("이그네아의 징표")}
                      </p>
                    </Button>
                  </div>

                  {/* 세계수 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-235px -47px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("세계수의 잎")}
                      </p>
                    </Button>
                  </div>

                  {/* 모험물 */}
                  <div style={{ textAlign: "center" }}>
                    <Button
                      style={{
                        backgroundColor: "transparent",
                        border: "none",
                        padding: "0px",
                      }}
                    >
                      <span
                        style={{
                          backgroundImage:
                            'url("https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/sprite/sprite_profile.png")',
                          backgroundPosition: "-235px -117px",
                          width: "20px",
                          height: "20px",
                          display: "inline-block",
                        }}
                      ></span>
                      <p style={{ margin: "5px 0 0", color: "#000" }}>
                        {getMaxPoint("항해 모험물")}
                      </p>
                    </Button>
                  </div>
                </div>
              </div>
            </Tab>
            <Tab eventKey="skill" title="보유캐릭터">
              보유캐릭터
            </Tab>
          </Tabs>
        </Col>
      </Row>
    </Container>
  );
}

export default ProfilePage;
