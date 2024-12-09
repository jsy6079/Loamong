import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./common/AuthContext";
import NavBar from "./common/NavBar";
import Main from "./mainSection/Main";
import ProfilePage from "./mainSection/ProfilePage";
import Join from "./sign/join";
import Login from "./sign/login";

function App() {
  return (
    <AuthProvider>
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/profile/:characterName" element={<ProfilePage />} />
          <Route path="/join" element={<Join />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
