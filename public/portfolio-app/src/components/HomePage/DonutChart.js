import React from 'react';
import { PieChart } from '@mui/x-charts/PieChart';

function DonutChart({ title, data }) {
  return (
    <div className="bg-white rounded-3 text-center pt-5 " style={{ maxWidth: '100%', overflowX: 'auto' }}>
      <div className="d-flex justify-content-center" style={{ minWidth: '300px', width: '100%', overflowX: 'auto' }}>
      <PieChart
        series={[
          {
            data,
            innerRadius: 80,
            outerRadius: 100,
            paddingAngle: 5,
            cornerRadius: 7,
          },
        ]}
        width={400}
        height={350}
        slotProps={{
          legend: {
            direction: 'column',
            position: { vertical: 'middle', horizontal: 'right' },
            itemMarkWidth: 9,
            itemMarkHeight: 9,
            markGap: 7,
            itemGap: 6,
          },
        }}
      /></div>
      <span className="fw-bold">{title}</span>
    </div>
  );
}

export default DonutChart;
