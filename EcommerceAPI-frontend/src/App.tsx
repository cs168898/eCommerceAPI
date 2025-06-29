import React, { useState, useCallback, useEffect, use } from "react";
import { useWebSocket } from "./hooks/useWebSocket";
import './App.css'
import Cookies from 'js-cookie';
import type { Product } from "./types/Product";

import { fetchProducts } from "./api/getAllProducts";
import { registerUser } from "./api/registerUser";
import RegisterForm from "./components/RegisterForm";

const App: React.FC = () => {
  const token = Cookies.get('jwt') ?? null;
  const [greetings, setGreetings] = useState<string[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [registerWindowVisible, setRegisterWindowVisible] = useState<boolean>(false);

  // create a function 
  const handleMessage = useCallback(
    (msg: string) => setGreetings((prev) => [...prev, msg]),
    []
  );

  function toggleRegisterWindow(): void {
    setRegisterWindowVisible(!registerWindowVisible);
  }

  // ================ ON PAGE LOAD ================

  useEffect(() => {
    if (token){
      // ts automatically parses the products json
      fetchProducts().then(setProducts);
      useWebSocket(token, handleMessage);
    }
  }, [token]);

  // ================= USER NOT LOGGED IN =====================
  if (!token) {
    return (
      <div>
         <div>Please Login / Register to view products.</div>
        <button> Log in </button>
        <button onClick={toggleRegisterWindow}> Register </button>
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
      <h1>WebSocket Demo</h1>
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
