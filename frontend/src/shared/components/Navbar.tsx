import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import "@fontsource/federo/400.css";
import NavigationMenu from "./NavigationMenu";
import { Outlet, useNavigate } from "react-router-dom";
import { useAuthContext } from "../contexts";

interface InavbarProps {
  sections: {
    cakesRef: React.RefObject<HTMLDivElement | null>;
    muffinsRef: React.RefObject<HTMLDivElement | null>;
    browniesRef: React.RefObject<HTMLDivElement | null>;
    docinhosRef: React.RefObject<HTMLDivElement | null>;
  };
}

const Navbar: React.FC<InavbarProps> = ({ sections }) => {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const isMenuOpen = Boolean(anchorEl);
  const navigate = useNavigate();
  const { logout } = useAuthContext();

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "right",
      }}
      keepMounted
      transformOrigin={{
        vertical: "top",
        horizontal: "right",
      }}
      open={isMenuOpen}
      onClose={handleMenuClose}
      slotProps={{ paper: { sx: { minWidth: 240 } } }}
    >
      <MenuItem onClick={() => navigate("/login")}>Log in</MenuItem>
      <MenuItem onClick={logout}>Log out</MenuItem>
      <MenuItem sx={{ display: "none" }} onClick={handleMenuClose}></MenuItem>
    </Menu>
  );

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <Typography
              variant="h6"
              noWrap
              component="a"
              href="/"
              sx={{
                mr: 2,
                fontFamily: "Federo",
                fontSize: "1.5rem",
                fontWeight: 700,
                color: "inherit",
                textDecoration: "none",
              }}
            >
              Pretty Cakes
            </Typography>
            <Box sx={{ ml: "auto" }}>
              <IconButton
                size="large"
                edge="end"
                aria-label="conta do usuário atual"
                aria-haspopup="true"
                onClick={handleProfileMenuOpen}
                color="secondary"
              >
                <AccountCircleIcon />
              </IconButton>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
      {renderMenu}
      <Box display="flex" justifyContent="center">
        <NavigationMenu sections={sections} />
      </Box>
      <Box height="100%">
        <Outlet />
      </Box>
    </Box>
  );
};

export default Navbar;
