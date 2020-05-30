

dictionary = {
    ' Kevin ': ' Hieu Nghia Huynh ',
    'jack6969x@gmail.com': 'hieunghia.huynh@connect.qut.edu.au',
    ' Audi ': ' Perdana ',
    'audibailey7@gmail.com': 'perdana.bailey@connect.qut.edu.au',
    ' TrevorWats ': ' Trevor Waturuocha ',
    '62232658+TrevorWats@users.noreply.github.com': 'trevor.waturuocha@connect.qut.edu.au',
    '49264143+JamieKieranMartin@users.noreply.github.com': 'jamie.martin@connect.qut.edu.au'
}

new_string = ""

if __name__ == "__main__":
    with open("log.txt") as file:
        for line in file:
            for key, value in dictionary.items():
                line = line.replace(key, value)

            new_string += line

    with open("log-formatted.txt", 'w') as file_out:
        file_out.write(new_string)


