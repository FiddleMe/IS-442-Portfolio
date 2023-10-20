import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Nav, NavItem, NavLink, Collapse } from 'reactstrap';
import { FaChevronDown, FaChevronUp, FaBars, FaHome, FaSignOutAlt } from 'react-icons/fa';
import { GoStack, GoGraph } from 'react-icons/go';
import './Sidebar.css';

function Sidebar(props) {
    const { subPages, currentPage } = props;
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [isMobile, setIsMobile] = useState(false);
    const [isSidebarOpen, setIsSidebarOpen] = useState(true);


    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    const pages = [
        { icon: FaHome, title: 'Home', subpages: [] },
        { icon: GoStack, title: 'Portfolios', subpages: subPages },
        { icon: GoGraph, title: 'Stocks', subpages: [] },
        { icon: FaSignOutAlt, title: 'Logout', subpages: [] }
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

    return (
        <div className={`col-md-2 pt-5 ${isSidebarOpen && !isMobile ? 'web_sidebar' : 'mobile_sidebar'}`}>
            {isMobile ? 
                <div className="sidebar-header">
                    <button className="hamburger-icon btn bg-transparent btn-outline-light mb-3" onClick={toggleSidebar}>
                        <FaBars color="#FFFFFF" />
                    </button>
                </div>
                :
                <div></div>
            }
            {
                isSidebarOpen ?
                    <Nav className="flex-column">
                        {pages.map((page, index) => {
                            return (
                                <NavItem key={index}>
                                    {page.subpages && page.subpages.length > 0 ? (
                                        <div>
                                            <NavLink
                                                onClick={toggleDropdown}
                                                className={`tab displayDropdown ${page.title === currentPage ? 'bold' : ''}`}
                                            >
                                                {page.icon()} {page.title}
                                                {isDropdownOpen ? <FaChevronUp /> : <FaChevronDown />}
                                            </NavLink>
                                            <Collapse isOpen={isDropdownOpen}>
                                                <div className={`pl-3 tab ${page.title === currentPage ? 'bold' : ''}`}>
                                                    {page.subpages.map((subpage, idx) => (
                                                        <p key={idx}>{subpage.icon()} {subpage.title}</p>
                                                    ))}
                                                </div>
                                            </Collapse>
                                        </div>
                                    ) : (
                                        <NavLink active={page.title === currentPage} href="#" className={`tab ${page.title === currentPage ? 'bold' : ''}`} >
                                            {page.icon()} {page.title}
                                        </NavLink>
                                    )}
                                </NavItem>
                            );
                        })}
                    </Nav> :
                    <div></div>
            }
        </div>

    );
};


export default Sidebar;
