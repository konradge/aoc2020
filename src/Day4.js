const reqFields = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"];
const regex = new RegExp(
  "((byr:(19[2-9][0-9]|200[0-2])|" +
    "iyr:20(1[0-9]|20)|" +
    "eyr:20(2[0-9]|30)|" +
    "hgt:((1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in)|" +
    "hcl:#[0-9a-f]{6}|" +
    "ecl:(amb|blu|brn|gry|grn|hzl|oth)|" +
    "pid:[0-9]{9})( )?){7}"
);

export const part1 = (input) => {
  return input
    .replace(/\r/g, "")
    .split(/\n\n/g)
    .map((e) => e.replace(/\n/g, " "))
    .filter((e) => reqFields.every((field) => e.includes(field))).length;
};

export const part2 = (input) => {
  return input
    .replace(/\r/g, "")
    .split(/\n\n/g)
    .map((e) => e.replace(/\n/g, " "))
    .filter((e) => reqFields.every((field) => e.includes(field)))
    .filter((passport) => {
      return passport.replace(/cid:\d+ ?/, "").match(regex);
    }).length;
};
