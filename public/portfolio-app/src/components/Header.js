import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaUserCircle } from 'react-icons/fa';

function Header(props) {
    const [isMobile, setIsMobile] = useState(false);
    useEffect(() => {
        const handleResize = () => {
            if (window.innerWidth <= 768) {
                setIsMobile(true);
            } else {
                setIsMobile(false);
            }
        };

        window.addEventListener('resize', handleResize);

        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return (
        <div>
            {isMobile ?
                <nav className="navbar navbar-dark bg-white justify-content-start">
                    <div className="text-dark px-2 py-2 align-items-center" style={{ display: "flex", flexDirection: "row" }}>
                        <FaUserCircle size={30} />
                        <div className='ms-3'>
                            <div className='text-left'>{props.name}</div>
                            <div className='text-left'>{props.email}</div>
                        </div>
                    </div>
                </nav>
                :
                <nav className="navbar navbar-dark bg-white justify-content-end">
                    <div className="text-dark px-4 py-1 align-items-center" style={{ display: "flex", flexDirection: "row" }}>
                        <div className='me-3'>
                            <div className='text-right'>{props.name}</div>
                            <div className='text-right'>{props.email}</div>
                        </div>
                        <FaUserCircle size={30} />

                    </div>
                </nav>
            }
        </div>
    );
}
export default Header