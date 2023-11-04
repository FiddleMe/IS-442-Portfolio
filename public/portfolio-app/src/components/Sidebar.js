import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Nav, NavItem, NavLink, Collapse } from 'reactstrap';
import { FaChevronDown, FaChevronUp, FaBars, FaHome, FaSignOutAlt } from 'react-icons/fa';
import { GoStack, GoGraph } from 'react-icons/go';
import { MdCreateNewFolder } from 'react-icons/md';
import './Sidebar.css';

function Sidebar(props) {
  const { subPages, currentPage } = props;
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isMobile, setIsMobile] = useState(false);
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const printPortfolio = (page) => {
    console.log(page);
  };

  const isSubPage = (page) => {
    for (let i = 0; i < pages.length; i++) {
      if (pages[i].subpages.find((subpage) => subpage.title === page)) {
        return true;
      }
    }
    return false;
  };

  const pages = [
    { icon: FaHome, title: 'Home', subpages: [], path: '/home' },
    { icon: GoStack, title: 'Portfolios', subpages: subPages , path: '/home'},
    { icon: MdCreateNewFolder, title: 'Create Portfolio', subpages: [], path: '/create-portfolio'},
    { icon: GoGraph, title: 'Stocks', subpages: [], path: '/stocks',},
    { icon: FaSignOutAlt, title: 'Logout', subpages: [], path: '/login' },
  ];

  const toggleSidebar = () => {
    if (isMobile) {
      setIsSidebarOpen(!isSidebarOpen);
    }
  };

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth <= 768) {
        setIsMobile(true);
        setIsSidebarOpen(false);
      } else {
        setIsMobile(false);
        setIsSidebarOpen(true);
      }
    };

    window.addEventListener('resize', handleResize);

    return () => window.removeEventListener('resize', handleResize);
  }, []);

  useEffect(() => {
    setIsDropdownOpen(isSubPage(currentPage));
  }, [currentPage]);

  return (
    // <div className={`col-md-2 ${isSidebarOpen && !isMobile ? 'web_sidebar' : 'mobile_sidebar'}`}>
    <div
      className={`col-md-2 ${
        isSidebarOpen && !isMobile
          ? 'web_sidebar'
          : isSidebarOpen && isMobile
          ? 'mobile_sidebar pb-3'
          : 'menu_closed'
      }`}
    >
      {isMobile ? (
        <div className="sidebar-header mt-3">
          <button
            className={`hamburger-icon btn bg-transparent  mb-3 ${
              isSidebarOpen ? 'btn-outline-light' : 'btn-outline-dark'
            }`}
            onClick={() => toggleSidebar()}
          >
            <FaBars color={isSidebarOpen ? '#FFFFFF' : '#4C506B'} />
          </button>
        </div>
      ) : (
        <div></div>
      )}
      {isSidebarOpen ? (
        <Nav className="flex-column">
          {pages.map((page, index) => {
            return (
              <NavItem key={index}>
                {page.subpages && page.subpages.length > 0 ? (
                  <div>
                    <NavLink
                      onClick={() => toggleDropdown()}
                      className={`tab displayDropdown ${page.title === currentPage ? 'bold' : ''}`}
                    >
                      {page.icon()} {page.title}
                      {isDropdownOpen ? <FaChevronUp /> : <FaChevronDown />}
                    </NavLink>
                    <Collapse isOpen={isDropdownOpen}>
                      <div className={`pl-3 ${page.title === currentPage ? 'bold' : ''}`}>
                        {page.subpages.map((subpage, idx) => (
                          <div
                            key={idx}
                            onClick={() => printPortfolio(subpage.title)}
                            className={`tab ${subpage.title === currentPage ? 'bold' : ''}`}
                          >
                            {subpage.icon()} {subpage.title}
                          </div>
                        ))}
                      </div>
                    </Collapse>
                  </div>
                ) : (
                  <NavLink
                    active={page.title === currentPage}
                    href={page.path}
                    className={`tab ${page.title === currentPage ? 'bold' : ''}`}
                  >
                    {page.icon()} {page.title}
                  </NavLink>
                )}
              </NavItem>
            );
          })}
        </Nav>
      ) : (
        <div></div>
      )}
    </div>
  );
}

export default Sidebar;
