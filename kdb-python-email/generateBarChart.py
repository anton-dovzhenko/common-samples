import pandas as pd
import matplotlib.pyplot as plt



raw_data = {'time': ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00'],
            'dailySpread': [10, 8, 7, 6, 5, 8, 9, 9.7],
            'weeklySpread': [11, 6, 5, 4, 3, 7, 10, 12.5]}
df = pd.DataFrame(raw_data, columns=['time', 'dailySpread', 'weeklySpread'])


# Setting the positions and width for the bars
pos = list(range(len(df['dailySpread'])))
width = 0.8

# Plotting the bars
fig, ax = plt.subplots(figsize=(3, 3))

# Create a bar with pre_score data,
# in position pos,

plt.scatter(pos,
        df['weeklySpread'],
        s=50,
        alpha=1,
        color='#229954',
        label=df['time'][0],
        )

plt.bar(pos,
        df['dailySpread'],
        width,
        alpha=1,
        color='#AED6F1',
        label=df['time'][0],
        zorder=-1
        )

ax.set_ylabel('Spread (pip)')
ax.yaxis.set_label_position('right')
ax.set_title('EURUSD (00:00 - 08:00)')
ax.set_xticklabels([])

# Adding the legend and showing the plot
#plt.legend(['Week Avg', 'Today'], loc='upper right')
#plt.grid()
plt.savefig('barChart.png')
#plt.show()