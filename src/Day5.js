export const part1 = (input) => {
  return Math.max(
    ...input
      .replace(/\r/g, "")
      .split("\n")
      .map((seat) => {
        return {
          row: parseInt(
            seat.slice(0, 8).replace(/B/g, "1").replace(/F/g, "0"),
            2
          ),
          col: parseInt(
            seat.slice(7, 10).replace(/R/g, "1").replace(/L/g, "0"),
            2
          ),
        };
      })
      .map((seat) => seat.row * 8 + seat.col)
  );
};

//Part 2
export const part2 = (input) => {
  return input
    .replace(/\r/g, "")
    .split("\n")
    .map((seat) => {
      return {
        row: parseInt(
          seat.slice(0, 8).replace(/B/g, "1").replace(/F/g, "0"),
          2
        ),
        col: parseInt(
          seat.slice(7, 10).replace(/R/g, "1").replace(/L/g, "0"),
          2
        ),
      };
    })
    .map((seat) => seat.row * 8 + seat.col)
    .sort((a, b) => a - b)
    .reduce((acc, seatId, index, allSeats) => {
      if (seatId + 2 == allSeats[index + 1]) {
        return seatId + 1;
      } else {
        return acc;
      }
    }, 0);
};
