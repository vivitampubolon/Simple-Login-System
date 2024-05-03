import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { MDBContainer, MDBInput, MDBBtn } from "mdb-react-ui-kit";

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const history = useNavigate();

  const handleLogin = async () => {
    try {
      if (!username || !password) {
        setError("Please enter both username and password.");
        return;
      }

      const response = await axios.post("http://localhost:8081/auth/signin", {
        username,
        password,
      });
      console.log("Login successful:", response.data);
      history("/dashboard");
    } catch (error) {
      console.error(
        "Login failed:",
        error.response ? error.response.data : error.message
      );
      setError("Invalid username or password.");
    }
  };

  return (
    <div className="flex justify-center items-center mt-20">
      <div
        className="border rounded-lg p-4 flex justify-center  bg-blue-200"
        style={{ width: "500px", height: "auto" }}
      >
        <MDBContainer className="p-3">
          <h2 className="mb-4 text-center">Login Page</h2>
          <MDBInput
            wrapperClass="mb-4"
            placeholder="Email address"
            id="email"
            value={username}
            type="email"
            onChange={(e) => setUsername(e.target.value)}
            className="border h-10 p-4 w-60 border-gray-500"
          />
          <MDBInput
            wrapperClass="mb-4"
            placeholder="Password"
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="border h-10 p-4 w-60 border-gray-500"
          />
          {error && <p className="text-danger">{error}</p>}{" "}
          {/* Render error message if exists */}
          <MDBBtn
            className="mb-4 d-block bg-blue-500 border border-slate-700"
            style={{ height: "50px", width: "100%" }}
            onClick={handleLogin}
          >
            <p className="text-md text-white font-sans"> Sign In </p>
          </MDBBtn>
          <div className="text-center ">
            <p>
              Not a member?{" "}
              <a href="/signup" className="text-blue-400">
                Register
              </a>
            </p>
          </div>
        </MDBContainer>
      </div>
    </div>
  );
}

export default LoginPage;
