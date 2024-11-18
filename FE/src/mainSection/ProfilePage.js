import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { Container } from "react-bootstrap";

function ProfilePage() {
  const { characterName } = useParams();
  const [profileData, setProfileData] = useState(null);

  const fetchProfileData = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/character/profile/${characterName}`
      );
      setProfileData(response.data);
    } catch (error) {
      console.log("프로필 데이터를 가져오지 못했습니다:", error);
    }
  };

  useEffect(() => {
    fetchProfileData();
  }, [characterName]);

  return (
    <Container style={{ marginTop: "50px" }}>
      <div>
        {profileData ? (
          <div>
            <h2>{profileData.CharacterName}의 프로필</h2>
            <p>서버: {profileData.ServerName}</p>
            <p>아이템 레벨: {profileData.ItemAvgLevel}</p>
            <img
              src={profileData.CharacterImage}
              alt={profileData.CharacterName}
            />
          </div>
        ) : (
          <p>해당 유저는 존재하지않습니다.</p>
        )}
      </div>
    </Container>
  );
}

export default ProfilePage;
