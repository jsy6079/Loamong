import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./common/NavBar";
import Main from "./mainSection/Main";
import ProfilePage from "./mainSection/ProfilePage";

function App() {
  return (
    <Router>
      <NavBar />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/profile/:characterName" element={<ProfilePage />} />
      </Routes>
    </Router>
  );
}

export default App;
