import React from 'react';
import { PieChart } from '@mui/x-charts/PieChart';

function DonutChart({ title, data }) {
  return (
    <div className="bg-white rounded-3 text-center">
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
      />
      <span className="fw-bold">{title}</span>
    </div>
  );
}

export default DonutChart;
