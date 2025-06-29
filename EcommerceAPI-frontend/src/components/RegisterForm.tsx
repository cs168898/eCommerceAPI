import React, { useState } from "react";

// interface defines the types of the props that the RegisterForm component will expect
interface RegisterFormProps {
  onRegister: (username: string, email: string, password: string) => void;
  onClose: () => void;
}

// declares this as a React Function Component that takes RegisterFormProps as its props.
const RegisterForm: React.FC<RegisterFormProps> = ({ onRegister, onClose }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    onRegister(username , email, password);
  }

  return (
    <div className="p-4 bg-white rounded shadow">
      <h2 className="text-lg font-bold mb-2">Register</h2>
      <form onSubmit={handleSubmit}>
         <input
          className="border p-2 mb-2 w-full"
          type="username"
          placeholder="username"
          value={username}
          onChange={e => setUsername(e.target.value)}
          required
        />
        <input
          className="border p-2 mb-2 w-full"
          type="email"
          placeholder="Email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
        />
        <input
          className="border p-2 mb-2 w-full"
          type="password"
          placeholder="Password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          required
        />
        <div className="flex gap-2">
          <button className="bg-blue-500 text-white px-4 py-2 rounded" type="submit">
            Register
          </button>
          <button className="bg-gray-300 px-4 py-2 rounded" type="button" onClick={onClose}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default RegisterForm;