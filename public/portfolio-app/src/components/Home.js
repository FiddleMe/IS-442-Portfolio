import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from './Sidebar';
import { FaBookOpen} from 'react-icons/fa';

const Home = () => {
    const subPages = [
        { icon: FaBookOpen, title: 'Portfolio 1'},
        { icon: FaBookOpen, title:  'Portfolio 2' },
    ];
  
    const currentPage = 'Portfolio 1'; 
  
    return (
      <div className="container-fluid">
        <div className="row">
          <Sidebar subPages={subPages} currentPage={currentPage} />
          <div className="col-md-9">
            <h1>Main Content</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
          </div>
        </div>
      </div>
    );
  };
  
  export default Home;