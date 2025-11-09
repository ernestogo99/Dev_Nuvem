import React from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import {
  type Ilogin,
  type loginData,
  loginSchema,
} from "../../shared/interfaces";
import { zodResolver } from "@hookform/resolvers/zod";
import { authService } from "../../shared/services/authservice";
import toast from "react-hot-toast";
import { useAuthContext } from "../../shared/contexts";
// --- Login Component ---
export const Login: React.FC = () => {
  // 1. State for managing the input fields

  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<loginData>({
    resolver: zodResolver(loginSchema),
  });
  const { login } = useAuthContext();

  // 2. Handler for the form submission
  const handleLogin = (data: Ilogin) => {
    authService.login(data).then((response) => {
      if (response instanceof Error) {
        toast.error(response.message);
      } else {
        toast.success("Login successful");
        console.log(response);
        localStorage.setItem("token", response.token);
        login();
        setTimeout(() => {
          navigate("/");
        }, 3000);
      }
    });
  };

  return (
    // The main container provides the split-screen layout
    <div style={styles.container}>
      {/* LEFT COLUMN: IMAGE (using pretty cake2.jpg) */}
      <div style={styles.imageColumn}>
        <img
          // Assuming 'pretty cake2.jpg' is in the public folder
          src="/pretty cake2.jpg"
          alt="Delicious Pretty Cake"
          style={styles.image}
        />
      </div>

      {/* RIGHT COLUMN: LOGIN FORM */}
      <div style={styles.formColumn}>
        <h2 style={styles.welcomeText}>Welcome to Pretty Cake!</h2>

        <form onSubmit={handleSubmit(handleLogin)} style={styles.form}>
          {/* E-mail Input Field */}
          <div style={styles.inputGroup}>
            <label htmlFor="email" style={styles.label}>
              E-mail
            </label>
            <input
              type="email"
              id="email"
              {...register("email")}
              required
              style={styles.input}
            />
            {errors.email && (
              <>
                <p style={styles.errorText}>{errors.email.message}</p>
              </>
            )}
          </div>

          {/* Password Input Field */}
          <div style={styles.inputGroup}>
            <label htmlFor="password" style={styles.label}>
              Password
            </label>
            <input
              type="password"
              id="password"
              {...register("password")}
              required
              style={styles.input}
            />
            {/* The eye icon for password visibility (visual placeholder) */}
            <span style={styles.eyeIcon}>👁️</span>
            {errors.password && (
              <>
                <p style={styles.errorText}>{errors.password.message}</p>
              </>
            )}
          </div>

          {/* Log-in Button */}
          <button type="submit" style={styles.loginButton}>
            Log-in
          </button>
        </form>
        <div style={styles.form}>
          <button onClick={() => navigate("/")} style={styles.loginButton}>
            See menu
          </button>
        </div>
      </div>
    </div>
  );
};

// --- CSS-in-JS Styles to Match Mockup (pretty cake.jpg) ---
const styles: { [key: string]: React.CSSProperties } = {
  container: {
    display: "flex",
    minHeight: "100vh",
    backgroundColor: "#fff",
    overflow: "hidden",
  },
  imageColumn: {
    flex: "1.2",
    position: "relative",
    backgroundColor: "#FFEB3B", // Background for the yellow tone
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    overflow: "hidden",
  },
  image: {
    width: "100%",
    height: "100%",
    objectFit: "cover",
    position: "absolute", // Allows full-bleed effect within the column
  },
  formColumn: {
    flex: "1",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    padding: "40px",
    backgroundColor: "white",
    borderRadius: "20px 0 0 20px", // Creates the rounded corner effect
    boxShadow: "-10px 0 20px rgba(0,0,0,0.05)",
    position: "relative",
    zIndex: 10,
  },
  welcomeText: {
    fontSize: "1.8em",
    marginBottom: "40px",
    fontWeight: "bold",
    color: "#333",
  },
  form: {
    width: "100%",
    maxWidth: "300px",
  },
  inputGroup: {
    marginBottom: "30px",
    position: "relative",
  },
  label: {
    fontSize: "0.9em",
    color: "#333",
    display: "block",
    marginBottom: "5px",
  },
  input: {
    width: "100%",
    padding: "10px 10px",
    border: "1px solid #FFD78F", // Border color matching the button
    borderRadius: "5px",
    boxSizing: "border-box",
  },
  eyeIcon: {
    position: "absolute",
    right: "10px",
    top: "35px",
    cursor: "pointer",
    color: "#999",
    fontSize: "0.8em",
  },
  loginButton: {
    width: "100%",
    padding: "15px",
    backgroundColor: "#FFD78F", // Yellow button color from the mockup
    color: "#333",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontWeight: "bold",
    fontSize: "1.1em",
    marginTop: "20px",
    boxShadow: "0 4px 6px rgba(0,0,0,0.1)",
  },
  errorText: {
    color: "#ff0404ff",
  },
};
