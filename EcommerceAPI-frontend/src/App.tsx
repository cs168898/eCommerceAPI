import React, { useState, useCallback, useEffect, use } from "react";
import { useWebSocket } from "./hooks/useWebSocket";
import './App.css'
import Cookies from 'js-cookie';

import type { Product } from "./types/Product";

import { fetchProducts } from "./api/getAllProducts";
import { registerUser } from "./api/registerUser";
import { loginUser } from "./api/loginUser";
import { generateToken } from "./api/generateToken";

import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";

const App: React.FC = () => {
  const token = Cookies.get('jwt') ?? null;
  const [greetings, setGreetings] = useState<string[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [registerWindowVisible, setRegisterWindowVisible] = useState<boolean>(false);
  const [loginWindowVisible, setLoginWindowVisible] = useState<boolean>(false);
  const [username, setUsername] = useState<string>("");

  // create a function 
  const handleMessage = useCallback(
    (msg: string) => setGreetings((prev) => [...prev, msg]),
    []
  );

  // =============== LOGIN WINDOW =================
  function toggleLoginWindow(): void {
    setLoginWindowVisible(!loginWindowVisible);
  }

  // =============== REGISTER WINDOW ==============

  function toggleRegisterWindow(): void {
    setRegisterWindowVisible(!registerWindowVisible);
  }

  // ================ ON PAGE LOAD ================

  useWebSocket(Cookies.get('jwt') ?? null, handleMessage);

  useEffect(() => {
    if (token){
      // ts automatically parses the products json
      fetchProducts().then(setProducts);
    }
  }, [token]);

  // ================= USER NOT LOGGED IN =====================
  if (!token) {
    return (
      <div>
        {
          (!loginWindowVisible && !registerWindowVisible ) && (
            <>
              <div>Please Login / Register to view products.</div>
              <button onClick={toggleLoginWindow}> Log in </button>
              <button onClick={toggleRegisterWindow}> Register </button>
            </>
          ) 
        }
         
        {loginWindowVisible && (
          <LoginForm
            onLogin={(email, password) => {
              loginUser(email, password)
                .then(async response => {
                  if(response.success){
                    //set cookie
                    alert("login successful: " + response.message);
                    setUsername(response.data.username);
                    // we can also add other user data here later on...

                    const token = await generateToken(response.data.email, password);

                    // generate token here
                    Cookies.set('jwt', token);
                    alert('Token: ' + token);
                  } else{
                    alert("Login failed: " + response.message);
                  }
                  
                  // close the login window
                  toggleLoginWindow();
                })
                .catch(error => {
                  if (error.response && error.response.data) {
                    // if it failed and there is a message from the backend, display it
                    alert("Login failed: " + error.response.data.message);
                  } else {
                    alert("Login failed: " + error.message);
                  }
                });
            }}
            onClose={toggleLoginWindow}
          />
        )
        }
        {registerWindowVisible && (
          <RegisterForm
            onRegister={(username, email, password) => {
              // call the registerUser API
              registerUser(username, email, password)
                .then(response => alert(response))
                .catch(error => {
                  if (error.response && error.response.data) {
                    // if it failed and there is a message from the backend, display it
                    alert("Registration failed: " + error.response.data);
                  } else {
                    alert("Registration failed: " + error.message);
                  }
                });
            }}
            onClose={toggleRegisterWindow}
          />
        )}
      </div>
      
     
    );
  }

  // ================= USER LOGGED IN ===================
  return (
    <div>
      <h1>Hello, {username}</h1>
      <ul>
        {greetings.map((g, i) => (
          <li key={i}>{g}</li>
        ))}
      </ul>
      <div>
        <ul>
          {products.map((product) => (
            <li key={product.id}>
              {product.name} - ${product.price} - Stock: {product.quantity}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App
