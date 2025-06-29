import React, { useState } from "react";

// interface defines the types of the props that the Login component will expect
interface LoginFormProps {
  onLogin: (email: string, password: string) => void;
  onClose: () => void;
}

// declares this as a React Function Component that takes LoginProps as its props.
const LoginForm: React.FC<LoginFormProps> = ({ onLogin, onClose }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    onLogin(email, password);
  }

  return (
    <div className="p-4 bg-white rounded shadow">
      <h2 className="text-lg font-bold mb-2">Login</h2>
      <form onSubmit={handleSubmit}>
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
            Login
          </button>
          <button className="bg-gray-300 px-4 py-2 rounded" type="button" onClick={onClose}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;